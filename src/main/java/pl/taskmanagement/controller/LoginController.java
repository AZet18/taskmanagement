package pl.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.taskmanagement.entity.User;
import pl.taskmanagement.service.UserService;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String showForm(Model model, HttpSession session) {
        if (userService.getLoggedUser(session).isPresent()) {
            return "redirect:/usertasks";
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session) {
        boolean success = userService.login(session, user.getEmail(), user.getPassword());
        if (success) {
            return "redirect:/usertasks";
        }
        return "login";
    }

}
