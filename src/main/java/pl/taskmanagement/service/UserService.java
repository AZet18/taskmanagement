package pl.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import pl.taskmanagement.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final String LOGGED_USER_EMAIL_ATTRIBUTE = "loggedUserEmail";
    private static final String LOGGED_USER_ID_ATTRIBUTE = "loggedUserId";
    private static final String LOGGED_USER_IS_ADMIN_ATTRIBUTE = "isAdmin";

    private final UserRepository userRepository;

    public boolean login(HttpSession session, String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .map(user -> {
                    session.setAttribute(LOGGED_USER_EMAIL_ATTRIBUTE, user.getEmail());
                    session.setAttribute(LOGGED_USER_ID_ATTRIBUTE, user.getId());
                    Boolean isAdmin = user.isAdmin();
                    session.setAttribute(LOGGED_USER_IS_ADMIN_ATTRIBUTE, isAdmin != null ? isAdmin : false);
                    return true;
                })
                .orElse(false);
    }

    public Optional<LoggedUser> getLoggedUser(HttpSession session) {
        Long id = (Long) session.getAttribute(LOGGED_USER_ID_ATTRIBUTE);
        String email = (String) session.getAttribute(LOGGED_USER_EMAIL_ATTRIBUTE);
        Boolean isAdmin = (Boolean) session.getAttribute(LOGGED_USER_IS_ADMIN_ATTRIBUTE);
        if (id != null && email != null) {
            return Optional.of(LoggedUser.of(id, email, isAdmin));
        }
        return Optional.empty();
    }

    public void logout(HttpSession session) {
        session.removeAttribute(LOGGED_USER_EMAIL_ATTRIBUTE);
        session.removeAttribute(LOGGED_USER_ID_ATTRIBUTE);
        session.removeAttribute(LOGGED_USER_IS_ADMIN_ATTRIBUTE);
        session.invalidate();
    }

    public boolean isAdmin(HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute(LOGGED_USER_IS_ADMIN_ATTRIBUTE);
        return isAdmin != null && isAdmin;
    }



    @Value(staticConstructor = "of")
    public static class LoggedUser {
        private Long id;
        private String email;
        private Boolean isAdmin;
    }
}
