package ru.itis.terziyan.diplom.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itis.terziyan.diplom.entities.User;
import ru.itis.terziyan.diplom.services.interfaces.UserService;

/**
 * Created by 1 on 28.05.2017.
 */
@Service
public class UserGetService {
    @Autowired
    private UserService userService;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        return user;
    }
}
