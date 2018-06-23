package ru.itis.terziyan.diplom.services.interfaces;

import ru.itis.terziyan.diplom.entities.Test;
import ru.itis.terziyan.diplom.entities.TestAnswer;
import ru.itis.terziyan.diplom.entities.TestQuestion;

import java.util.List;

public interface TestService {
    void save(Test test);
    Test getTest(Long id);
    List<Test> findAllTests();
    List<TestQuestion> getAllQuestionsByTest(Test test);
    void saveQuestion(TestQuestion testQuestion);
    void saveAnswer(TestAnswer testAnswer);
    List<Test> getAllTestsByCompetency(String competency);
}
