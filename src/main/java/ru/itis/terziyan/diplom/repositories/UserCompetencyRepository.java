package ru.itis.terziyan.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.terziyan.diplom.entities.Competency;
import ru.itis.terziyan.diplom.entities.User;
import ru.itis.terziyan.diplom.entities.UserCompetency;

import java.util.List;

@Repository
public interface UserCompetencyRepository extends JpaRepository<UserCompetency, Long> {

    UserCompetency findByUserAndCompetency(User user, Competency competency);

    List<UserCompetency> findAllByUser(User user);

}
