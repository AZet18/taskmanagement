package pl.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        if (taskService.findTaskByUserTaskId(taskId).isEmpty()) {
            taskService.deleteTask(taskId);
            session.setAttribute("messageType", "success");
            session.setAttribute("message", "Task successfully deleted.");
        } else {
            session.setAttribute("messageType", "danger");
            session.setAttribute("message", "Cannot delete task because it is associated with user tasks.");
            //redirectAttributes.addFlashAttribute
        }
        return "redirect:/admin/tasks";
    }

}
