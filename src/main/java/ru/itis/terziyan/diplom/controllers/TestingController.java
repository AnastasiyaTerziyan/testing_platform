package ru.itis.terziyan.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.terziyan.diplom.entities.forms.AnswersForm;
import ru.itis.terziyan.diplom.entities.forms.TestAnswerForm;
import ru.itis.terziyan.diplom.services.impl.UserGetService;
import ru.itis.terziyan.diplom.services.interfaces.TestService;
import ru.itis.terziyan.diplom.entities.*;
import ru.itis.terziyan.diplom.repositories.*;

import java.util.*;

@Controller
@RequestMapping("/game/")
public class TestingController {

    @Autowired
    TestService testService;
    @Autowired
    UserGetService userGetService;
    @Autowired
    TestAnswerRepository testAnswerRepository;
    @Autowired
    TestQuestionRepository testQuestionRepository;
    @Autowired
    CompetencyRepository competencyRepository;
    @Autowired
    UserCompetencyRepository userCompetencyRepository;
    @Autowired
    UserRepository userRepository;


    @GetMapping
    public String index(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        List<Test> tests = new ArrayList<>();
        List<TestAnswer> userAnswers = testAnswerRepository.findAllByUser(userGetService.getCurrentUser());
        if (userAnswers == null || userAnswers.isEmpty()) {
            Test baseTest;
            try {
                baseTest = testService.getAllTestsByCompetency(null).get(0);
            } catch (IndexOutOfBoundsException e) {
                baseTest = new Test();
            }
            tests = new ArrayList<>();
            tests.add(baseTest);
        } else {
            for (TestAnswer answ : userAnswers) {
                if (answ.getIsCorrect()) {
                    if (answ.getTestQuestion().getComp() != null) {
                        tests.addAll(testService.getAllTestsByCompetency(answ.getTestQuestion().getComp()));
                    } else {
                        tests.addAll(testService.getAllTestsByCompetency(answ.getTestQuestion().getTest().getTestComp()));
                    }
                }
            }
        }
        model.addAttribute("tests", tests);
        return "game";
    }

    @GetMapping("/answer/")
    public String writeAnswer(Model model, AnswersForm testAnswer) {
        User user = userGetService.getCurrentUser();
        Map<String, Double> compMap = new HashMap<>();
        Double levelOfComp = 0.0;
        boolean countTotal = false;
        Double userCoins = user.getCoins() == null ? 0.0 : user.getCoins();
        Set<TestQuestion> testQuestions = new HashSet<>();
        for (TestAnswerForm answer : testAnswer.getAnswers()) {
            TestQuestion testQuestion = testQuestionRepository.findOne(answer.getQuestionId());
            Integer totalQuestions = testQuestion.getTest().getTestQuestions().size();
            TestAnswer readyTestAnswer = new TestAnswer();
            readyTestAnswer.setTestQuestion(testQuestion);
            readyTestAnswer.setUser(user);
            readyTestAnswer.setUserAnswer(answer.getAnswerText());
            if (readyTestAnswer.getUserAnswer().equals(readyTestAnswer.getTestQuestion().getAnswer())) {
                readyTestAnswer.setIsCorrect(true);
            } else {
                readyTestAnswer.setIsCorrect(false);
            }
            String competency = "";
            Double currentDifficulty = 0.0;
            switch (testQuestion.getLevel()) {
                case LOW:
                    currentDifficulty = 0.5;
                    break;
                case MEDIUM:
                    currentDifficulty = 1.0;
                    break;
                case HARD:
                    currentDifficulty = 1.5;
                    break;
            }
            userCoins += ((1.0 / totalQuestions) * currentDifficulty) * 10;
            if (testQuestion.getComp().isEmpty()) {
                competency = testQuestion.getTest().getTestComp();
                testQuestions = testQuestion.getTest().getTestQuestions();
                countTotal = true;
                levelOfComp += readyTestAnswer.getIsCorrect() ? 1 * currentDifficulty : 0 * currentDifficulty;
                compMap.put(competency, levelOfComp);
            } else {
                competency = testQuestion.getComp();
                Double totalDifficulty = 0.5;
                levelOfComp = readyTestAnswer.getIsCorrect() ? 1 * currentDifficulty : 0 * currentDifficulty;
                compMap.put(competency, levelOfComp);
            }
            testAnswerRepository.save(readyTestAnswer);
        }

        for (Map.Entry<String, Double> entry : compMap.entrySet()) {
            User currUser = userGetService.getCurrentUser();
            Competency competency = competencyRepository.findByCompetencyName(entry.getKey());
            UserCompetency userCompetency = userCompetencyRepository.findByUserAndCompetency(userGetService.getCurrentUser(), competency);
            Double total = 0.5;
            if (countTotal) {
                total = testQuestions.stream().map(TestQuestion::getLevel).map(tl -> {
                    Double level = 0.0;
                    switch (tl) {
                        case LOW:
                            level = 0.5;
                            break;
                        case MEDIUM:
                            level = 1.0;
                            break;
                        case HARD:
                            level = 1.5;
                            break;
                    }
                    return level;
                }).mapToDouble(Double::doubleValue).sum();
            }
            if (userCompetency != null) {
                userCompetency.setCompetencyLevel((100.0 / total) * entry.getValue());
            } else {
                userCompetency = new UserCompetency();
                userCompetency.setUser(currUser);
                userCompetency.setCompetency(competency);
                userCompetency.setCompetencyLevel((100.0 / total) * entry.getValue());
            }
            userCompetencyRepository.save(userCompetency);
        }
        user.setCoins(userCoins);
        userRepository.save(user);
        return "redirect:/game/";
    }

    @GetMapping("/test/")
    public String getTestQuestions(Long testId, Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        Test test = testService.getTest(testId);
        List<TestQuestion> questions = testService.getAllQuestionsByTest(test);
        model.addAttribute("tests", test);
        model.addAttribute("questions", questions);
        model.addAttribute("testAnswer", new AnswersForm());
        return "game";
    }

    @GetMapping("/polls/")
    public String polls(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        return "polls";
    }

    @GetMapping("/score/")
    public String score(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        return "score";
    }
}
