package ru.itis.terziyan.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.terziyan.diplom.entities.Role;
import ru.itis.terziyan.diplom.entities.User;
import ru.itis.terziyan.diplom.entities.UserCompetency;
import ru.itis.terziyan.diplom.repositories.UserCompetencyRepository;
import ru.itis.terziyan.diplom.services.impl.ContactsServiceImpl;
import ru.itis.terziyan.diplom.services.impl.UserGetService;
import ru.itis.terziyan.diplom.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by unlim_000 on 23.05.2017.
 */

@Controller
@RequestMapping("/userprofile")
public class UserProfileController {


    @Autowired
    ContactsServiceImpl contactsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserGetService userGetService;
    @Autowired
    private UserCompetencyRepository userCompetencyRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String profile(Model model) {
        List<UserCompetency> userCompetencies = userCompetencyRepository.findAllByUser(userGetService.getCurrentUser());
        model.addAttribute("title", "Профиль");
        model.addAttribute("user_info", userGetService.getCurrentUser());
        model.addAttribute("userComp", userCompetencies);
        return "profile/profile";
    }

    @RequestMapping("/schedule/")
    public String schedule(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        model.addAttribute("title", "Расписание");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        return "profile/schedule";
    }

    @RequestMapping("/labs/")
    public String labs(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        model.addAttribute("title", "Презентации лабораторий");
        return "profile/labs";
    }

    @RequestMapping("/trainership/")
    public String training(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByEmail(auth.getName());
        model.addAttribute("title", "Стажировки");
        return "profile/trainership";
    }

    @RequestMapping("/hack/")
    public String hackathon(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByEmail(auth.getName());
        model.addAttribute("title", "Хакатоны");
        return "profile/hackathons";
    }

    @RequestMapping("/decan/")
    public String decanat(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        model.addAttribute("title", "Контакты деканата");
        List<User> contacts = contactsService.getContacts();
        model.addAttribute("contacts", contacts);
        return "profile/decan";
    }

    @RequestMapping("/references/{who}")
    public String refs(Model model, @PathVariable(required = false) String who) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        List<Role> currentRoles = new ArrayList<>();
        currentRoles = userGetService.getCurrentUser().getRoles().stream()
                .filter(role -> (role.getName().equals("ADMIN")))
                .collect(Collectors.toList());


        model.addAttribute("title", "Справки");
        return "profile/references";
    }

    @RequestMapping("/issues/")
    public String show_issues(Model model){
        model.addAttribute("user_info", userGetService.getCurrentUser());
        return "issues_show";
    }

}
