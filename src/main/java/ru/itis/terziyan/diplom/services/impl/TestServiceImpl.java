package ru.itis.terziyan.diplom.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.terziyan.diplom.entities.Test;
import ru.itis.terziyan.diplom.entities.TestAnswer;
import ru.itis.terziyan.diplom.entities.TestQuestion;
import ru.itis.terziyan.diplom.repositories.TestAnswerRepository;
import ru.itis.terziyan.diplom.repositories.TestQuestionRepository;
import ru.itis.terziyan.diplom.repositories.TestRepository;
import ru.itis.terziyan.diplom.services.interfaces.TestService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestQuestionRepository testQuestionRepository;

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    @Override
    @Transactional
    public void save(Test test) {
        testRepository.save(test);
    }

    @Override
    @Transactional
    public Test getTest(Long id) {
        return testRepository.getOne(id);
    }

    @Override
    public List<Test> findAllTests() {
        return testRepository.findAll();
    }

    @Override
    @Transactional
    public List<TestQuestion> getAllQuestionsByTest(Test test) {
        return testQuestionRepository.findAllByTest(test);
    }

    @Override
    @Transactional
    public void saveQuestion(TestQuestion testQuestion) {
        testQuestionRepository.save(testQuestion);
    }

    @Override
    @Transactional
    public void saveAnswer(TestAnswer testAnswer) {

    }

    @Override
    @Transactional
    public List<Test> getAllTestsByCompetency(String competency) {
        return testRepository.findAllByTestComp(competency);
    }
}
