package pl.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.taskmanagement.entity.Status;
import pl.taskmanagement.entity.UserTask;
import pl.taskmanagement.service.TaskService;
import pl.taskmanagement.service.UserService;
import pl.taskmanagement.service.UserTaskService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Controller
public class UserTaskController {

    private final UserTaskService userTaskService;
    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/usertasks")
    public String showUserTasks(HttpSession session, Model model) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/login";
        }

        Long userId = (Long) session.getAttribute("loggedUserId");
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("userTasks", userTaskService.getUserTasksByUserId(userId));
        model.addAttribute("statuses", Status.values());
        model.addAttribute("userTask", new UserTask());
        return "userTasks";
    }

    @PostMapping("/usertasks/add")
    public String addUserTask(HttpSession session, @RequestParam Long taskId
            , @ModelAttribute UserTask userTask
            , @RequestParam("deadline") String deadlineString) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/login";
        }

        Long userId = (Long) session.getAttribute("loggedUserId");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deadline = LocalDate.parse(deadlineString, formatter);
        userTask.setDeadline(deadline);
        userTaskService.addUserTask(userId, taskId, userTask);

        return "redirect:/usertasks";
    }

    private boolean isUserLoggedIn(HttpSession session) {
        return userService.getLoggedUser(session).isPresent();
    }
}