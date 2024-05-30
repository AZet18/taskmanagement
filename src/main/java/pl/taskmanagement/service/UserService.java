package pl.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import pl.taskmanagement.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final String LOGGED_USER_EMAIL_ATTRIBUTE = "loggedUserEmail";
    private static final String LOGGED_USER_ID_ATTRIBUTE = "loggedUserId";

    private final UserRepository userRepository;

    public boolean login(HttpSession session, String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .map(user -> {
                    session.setAttribute(LOGGED_USER_EMAIL_ATTRIBUTE, user.getEmail());
                    session.setAttribute(LOGGED_USER_ID_ATTRIBUTE, user.getId());
                    return true;
                })
                .orElse(false);

    }

    public Optional<LoggedUser> getLoggedUser(HttpSession session) {
        Long id = (Long) session.getAttribute(LOGGED_USER_ID_ATTRIBUTE);
        String email = (String) session.getAttribute(LOGGED_USER_EMAIL_ATTRIBUTE);
        if (id != null && email != null) {
            return Optional.of(LoggedUser.of(id, email));
        }
        return Optional.empty();
    }

    public void logout(HttpSession session) {
        session.removeAttribute(LOGGED_USER_EMAIL_ATTRIBUTE);
        session.removeAttribute(LOGGED_USER_ID_ATTRIBUTE);
        session.invalidate();
    }

    @Value(staticConstructor = "of")
    public static class LoggedUser {
        private Long id;
        private String email;
    }
}
