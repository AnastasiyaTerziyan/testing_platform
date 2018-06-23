package ru.itis.terziyan.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.terziyan.diplom.entities.User;
import ru.itis.terziyan.diplom.services.impl.UserGetService;
import ru.itis.terziyan.diplom.services.interfaces.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by unlim_000 on 10.05.2017.
 */

@Controller
public class AuthController {
    @Autowired
    private UserService userService;


    @Autowired
    private UserGetService userGetService;

    @RequestMapping(value = "/access-denied")
    public String ad() {
        return "accessdenied";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "redirect:/#loginModal";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("title", "Регистрация нового пользователя");
        return "register";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model, HttpServletRequest request) {
        User userExists = userService.findByEmail(user.getEmail());
        String[] roles = { "STUDENT" };
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "*Уже существует пользователь с данным email");
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            bindingResult
                    .rejectValue("confirmMessage", "error.user",
                            "*Пароли должны совпадать");
        }
        if (!bindingResult.hasErrors()) {
            user.setCoins(0.0);
            userService.save(user, roles);
            model.addAttribute("successMessage", "Пользователь успешно зарегистрирован");
            model.addAttribute("user", new User());
            model.addAttribute("user_info", userGetService.getCurrentUser());
        }
        model.addAttribute("title", "Регистрация нового пользователя");
        model.addAttribute("user_info", userGetService.getCurrentUser());
        return "register";
    }
}
