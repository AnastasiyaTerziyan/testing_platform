package ru.itis.terziyan.diplom.services.impl;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;
import org.apache.commons.io.FileUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.terziyan.diplom.entities.User;
import ru.itis.terziyan.diplom.entities.forms.MessageForm;
import ru.itis.terziyan.diplom.repositories.RoleRepository;
import ru.itis.terziyan.diplom.repositories.UserRepository;
import ru.itis.terziyan.diplom.services.interfaces.MessageService;
import ru.itis.terziyan.diplom.services.interfaces.UserService;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by unlim_000 on 10.05.2017.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MessageService messageService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void save(User user, String[] roles) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findByNameIn(Arrays.asList(roles))));

        try {
            URL website = new URL("https://abiturient.kpfu.ru/entrant/docs/F567497354/01_1430_ot_03.08.2016.pdf");
            FileUtils.copyURLToFile(website, new File("priem.pdf"));
            Document pdf = PDF.open("priem.pdf");
            StringBuilder text = new StringBuilder(1024);
            pdf.pipe(new OutputTarget(text));
            pdf.close();
            String nameToCheck = user.getLastName() + " " + user.getName() + " " + user.getMidName();
            if (text.toString().contains(nameToCheck)) {
                user.setStudent(true);
            } else {
                user.setStudent(false);
            }
            System.out.println(text);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        User save = userRepository.save(user);

        messageService.saveMessage(save.getId(), new MessageForm(1l, "Привет, есть вопросы? Можешь задать их мне."));
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        User byEmail = userRepository.findByEmail(email);
        if (byEmail != null) {
            Hibernate.initialize(byEmail.getRoles());
            Hibernate.initialize(byEmail.getMaterials());
        }
        return byEmail;
    }


    @Override
    public List<User> findUserByEmailLike(String term) {
        return userRepository.findByEmailContaining(term);
    }

}
