package pl.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.taskmanagement.entity.Task;
import pl.taskmanagement.entity.User;
import pl.taskmanagement.service.TaskService;
import pl.taskmanagement.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/")
    public String getTasks(Model model, HttpSession session) {
        if (!isUserLoggedIn(session)) {
            model.addAttribute("user", new User());
            return "login";
        } else {
            model.addAttribute("tasks", taskService.getAllTasks());
        }
        return "tasks";
    }

    @GetMapping("/tasks")
    public String showTasks(HttpSession session, Model model) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/login";
        }
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        userService.logout(session);
        return "redirect:/login";
    }

    private boolean isUserLoggedIn(HttpSession session) {
        return userService.getLoggedUser(session).isPresent();
    }
}
