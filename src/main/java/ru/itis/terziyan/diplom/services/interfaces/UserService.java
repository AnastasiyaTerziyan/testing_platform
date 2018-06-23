package ru.itis.terziyan.diplom.services.interfaces;

import ru.itis.terziyan.diplom.entities.User;

import java.util.List;

/**
 * Created by unlim_000 on 10.05.2017.
 */
public interface UserService {
    void save(User user, String[] roles);

    User findByEmail(String email);

    List<User> findUserByEmailLike(String term);
}
