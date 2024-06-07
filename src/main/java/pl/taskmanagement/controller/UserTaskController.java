package pl.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.taskmanagement.controller.model.AddUserTaskViewModel;
import pl.taskmanagement.controller.model.UserTaskViewModel;
import pl.taskmanagement.entity.*;
import pl.taskmanagement.service.TagService;
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
    private final TagService tagService;

    @GetMapping("/usertasks")
    public String showUserTasks(HttpSession session, Model model) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/login";
        }

        Long userId = (Long) session.getAttribute("loggedUserId");
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("userTasks", getUserTasks(userId));
        model.addAttribute("statuses", Status.values());
        model.addAttribute("tags", tagService.getAllTags());
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

        userTaskService.deleteUserTask(id, loggedUser.getId());
        return "redirect:/usertasks";
    }

    @GetMapping("/usertasks/edit/{id}")
    public String editUserTask(@PathVariable Long id, HttpSession session, Model model) {
        UserService.LoggedUser loggedUser = getLoggedUser(session);
        if (loggedUser == null) {
            return "redirect:/login";
        }

        UserTask userTask = userTaskService.findById(id);
        if (!userTask.getUser().getId().equals(loggedUser.getId())) {
            return "redirect:/usertasks";
        }

        model.addAttribute("userTask", userTask);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("tags", tagService.getAllTags());

        return "editUserTask";
    }

    @PostMapping("/usertasks/update")
    public String updateUserTask(HttpSession session, @RequestParam List<Long> tagIds, @RequestParam Long taskId, @ModelAttribute UserTask userTask) {
        UserService.LoggedUser loggedUser = getLoggedUser(session);
        if (loggedUser == null) {
            return "redirect:/login";
        }

        if (!userTask.getUser().getId().equals(loggedUser.getId())) {
            return "redirect:/usertasks";
        }
        Task task = taskService.findById(taskId);
        userTask.setTask(task);
        userTaskService.updateTask(userTask.getId(), userTask, tagIds);

        return "redirect:/usertasks";
    }

    private boolean isUserLoggedIn(HttpSession session) {
        return getLoggedUser(session) != null;
    }


    private UserService.LoggedUser getLoggedUser(HttpSession httpSession) {
        return userService.getLoggedUser(httpSession).orElse(null);
    }

    private List<UserTaskViewModel> getUserTasks(Long userId) {
        List<UserTask> userTasks = userTaskService.getUserTasksByUserId(userId);
        List<UserTaskViewModel> userTaskViewModels = userTasks.stream()
                .map(UserTaskViewModel::of)
                .collect(Collectors.toList());
        userTaskViewModels.forEach(userTaskViewModel -> userTaskViewModel.setTags(userTaskService.getTagsForUserTask(userTaskViewModel.getId())));
        return userTaskViewModels;
    }

}


