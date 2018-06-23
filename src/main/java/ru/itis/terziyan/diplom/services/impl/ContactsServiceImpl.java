package ru.itis.terziyan.diplom.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.terziyan.diplom.entities.User;
import ru.itis.terziyan.diplom.repositories.RoleRepository;
import ru.itis.terziyan.diplom.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Ostapenko on 01.01.1970.
 */

@Service("contactsService")
public class ContactsServiceImpl {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    public List<User> getContacts() {
        List<String> roles = new ArrayList<>();
        roles.add("DECAN");
        List<User> contacts = userRepository.findAllByRoles(roleRepository.findByNameIn(roles));
        return contacts;
    }
}
