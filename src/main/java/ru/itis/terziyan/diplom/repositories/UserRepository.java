package ru.itis.terziyan.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.terziyan.diplom.entities.Role;
import ru.itis.terziyan.diplom.entities.User;

import java.util.List;

/**
 * Created by unlim_000 on 10.05.2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findAllByRoles(List<Role> roles);

    List<User> findByEmailContaining(String email);
}
