package ru.itis.terziyan.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.terziyan.diplom.entities.Test;
import ru.itis.terziyan.diplom.entities.TestQuestion;

import java.util.List;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, Long> {
    List<TestQuestion> findAllByTest(Test test);
    List<TestQuestion> findAllByComp(String comp);
}
