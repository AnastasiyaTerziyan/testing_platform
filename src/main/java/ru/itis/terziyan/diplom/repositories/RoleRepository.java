package ru.itis.terziyan.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.terziyan.diplom.entities.Role;

import java.util.List;

/**
 * Created by unlim_000 on 10.05.2017.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String role);

    List<Role> findByNameIn(List<String> roles);
}
