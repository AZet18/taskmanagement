package pl.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GetMapping("/")
    public String getTasks(Model model, HttpSession session) {
        if (userService.getLoggedUser(session).isEmpty()) {
            model.addAttribute("user", new User());
            return "login";
        } else {
            model.addAttribute("tasks", taskService.getAllTasks());
        }
        return "tasks";
    }

    @GetMapping("/tasks")
    public String showTasks(HttpSession session, Model model) {
        if (userService.getLoggedUser(session).isEmpty()) {
            return "redirect:/login";
        }
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        logger.debug("CZY DZIAŁA LOGOUT");
        userService.logout(session);
        return "redirect:/login";
    }


}
