package pl.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.taskmanagement.entity.Task;
import pl.taskmanagement.service.TaskService;
import pl.taskmanagement.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/tasks")
    public String showAdminTasks(HttpSession session, Model model) {
        UserService.LoggedUser loggedUser = userService.getLoggedUser(session).orElse(null);
        if (loggedUser == null || !userService.isAdmin(session)) {
            return "redirect:/login";
        }
        model.addAttribute("tasks", taskService.getAllTasks());
        return "tasks";
    }


    @GetMapping("/tasks/add")
    public String showAddTaskForm(HttpSession session, Model model) {
        UserService.LoggedUser loggedUser = userService.getLoggedUser(session).orElse(null);
        if (loggedUser == null || !userService.isAdmin(session)) {
            return "redirect:/login";
        }
        model.addAttribute("task", new Task());
        return "adminAddTaskForm";
    }

    @PostMapping("/tasks/add")
    public String addTask(HttpSession session, @ModelAttribute("task") Task task) {
        UserService.LoggedUser loggedUser = userService.getLoggedUser(session).orElse(null);
        if (loggedUser == null || !userService.isAdmin(session)) {
            return "redirect:/login";
        }
        taskService.addTask(task);
        return "redirect:/admin/tasks";
    }

    @PostMapping("/tasks/delete/{taskId}")
    public String deleteTask(HttpSession session, @PathVariable Long taskId) {
        UserService.LoggedUser loggedUser = userService.getLoggedUser(session).orElse(null);
        if (loggedUser == null || !userService.isAdmin(session)) {
            return "redirect:/login";
        }
        taskService.deleteTask(taskId);
        return "redirect:/admin/tasks";
    }

}
