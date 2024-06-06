package pl.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.taskmanagement.controller.model.AddUserTaskViewModel;
import pl.taskmanagement.controller.model.UserTaskViewModel;
import pl.taskmanagement.entity.Status;
import pl.taskmanagement.entity.Task;
import pl.taskmanagement.entity.User;
import pl.taskmanagement.entity.UserTask;
import pl.taskmanagement.service.TaskService;
import pl.taskmanagement.service.UserService;
import pl.taskmanagement.service.UserTaskService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

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
        model.addAttribute("userTasks", getUserTasks(userId));
        model.addAttribute("statuses", Status.values());
//        model.addAttribute("userTask", new UserTask());
        return "userTasks";
    }

//    @PostMapping("/usertasks/add")
//    public String addUserTask(HttpSession session, @RequestParam Long taskId
//            , @ModelAttribute UserTask userTask
//            , @RequestParam("deadline") String deadlineString) {
//        if (!isUserLoggedIn(session)) {
//            return "redirect:/login";
//        }
//
//        Long userId = (Long) session.getAttribute("loggedUserId");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate deadline = LocalDate.parse(deadlineString, formatter);
//        userTask.setDeadline(deadline);
//        userTaskService.addUserTask(userId, taskId, userTask);
//
//        return "redirect:/usertasks";
//    }

    @PostMapping("/usertasks/add")
    public String addUserTask(HttpSession session, @ModelAttribute AddUserTaskViewModel addUserTaskViewModel) {
        UserService.LoggedUser loggedUser = getLoggedUser(session);
        if (loggedUser == null) {
            return "redirect:/login";
        }

        userTaskService.addUserTask(addUserTaskViewModel.asParams(loggedUser.getId()));

        return "redirect:/usertasks";
    }

    @PostMapping("/usertasks/delete/{id}")
    public String deleteUserTask(HttpSession session, @PathVariable Long id) {
        UserService.LoggedUser loggedUser = getLoggedUser(session);
        if (loggedUser == null) {
            return "redirect:/login";
        }
        System.out.println(id);

//        userTaskService.deleteUserTask(userTask.getUser().getId(), loggedUser.getId());
        userTaskService.deleteUserTask(id, loggedUser.getId());
        throw new RuntimeException("ERRRRRORRRRRR");
//        return "redirect:/usertasks";
    }

    private boolean isUserLoggedIn(HttpSession session) {
        return getLoggedUser(session) != null;
    }


    private UserService.LoggedUser getLoggedUser(HttpSession httpSession) {
        return userService.getLoggedUser(httpSession).orElse(null);
    }

    private List<UserTaskViewModel> getUserTasks(Long userId) {
        return userTaskService.getUserTasksByUserId(userId)
                .stream()
                .map(UserTaskViewModel::of)
                .collect(Collectors.toList());
    }
}


