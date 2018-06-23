package ru.itis.terziyan.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.terziyan.diplom.entities.TestAnswer;
import ru.itis.terziyan.diplom.entities.User;

import java.util.List;

@Repository
public interface TestAnswerRepository extends JpaRepository<TestAnswer, Long> {
    List<TestAnswer> findAllByUser(User user);
}
