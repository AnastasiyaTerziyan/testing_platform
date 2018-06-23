package ru.itis.terziyan.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import ru.itis.terziyan.diplom.entities.forms.TestRequest;
import ru.itis.terziyan.diplom.repositories.CompetencyRepository;
import ru.itis.terziyan.diplom.services.MailClient;
import ru.itis.terziyan.diplom.services.impl.UserGetService;
import ru.itis.terziyan.diplom.entities.Competency;
import ru.itis.terziyan.diplom.entities.Materials;
import ru.itis.terziyan.diplom.entities.Test;
import ru.itis.terziyan.diplom.entities.TestQuestion;
import ru.itis.terziyan.diplom.services.interfaces.MaterialsService;
import ru.itis.terziyan.diplom.services.interfaces.TestService;
import ru.itis.terziyan.diplom.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Created by unlim_000 on 31.05.2017.
 */

@RestController
public class AjaxController {

    @Autowired
    UserService userService;
    @Autowired
    UserGetService userGetService;
    @Autowired
    MailClient mailClient;
    @Autowired
    MaterialsService materialsService;
    @Autowired
    TestService testService;
    @Autowired
    CompetencyRepository competencyRepository;


    @PostMapping("/api/support")
    public ResponseEntity<?> sendSupportMail(@RequestParam String subject, @RequestParam String message) {
        try {
            mailClient.prepareAndSendSupport(subject, message, userGetService.getCurrentUser().getEmail());
        } catch (MailException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка в отправке сообщения");
        }
        return ResponseEntity.ok("Ваше письмо отправлено");
    }

    @GetMapping(value = "/api/materials", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<Materials>> getMaterials(@RequestParam Integer course, @RequestParam Integer semester,
                                                 @RequestParam String subject) {
        List<Materials> filteredMaterials = materialsService.getAllByCourseAndSemesterAndSubject(course, semester, subject);
        filteredMaterials.forEach(materials -> {
            materials.setUser(null);
        });
        return new ResponseEntity<>(filteredMaterials, HttpStatus.OK);
    }

    @PostMapping("/api/createTest")
    public ResponseEntity<List<TestQuestion>> saveQuestion(@RequestBody TestRequest testRequest) {

        if (testRequest.testQuestion.getQuestionType() == null) {
            testService.save(testRequest.test);
            if (testRequest.test.getTestComp() != null && !testRequest.test.getTestComp().isEmpty()
                    && competencyRepository.findByCompetencyName(testRequest.test.getTestComp()) == null) {
                Competency comp = new Competency();
                comp.setCompetencyName(testRequest.test.getTestComp());
                competencyRepository.save(comp);
            }
            List<TestQuestion> testQuestions = new ArrayList<>();
            TestQuestion emptyTestQ = new TestQuestion();
            emptyTestQ.setTest(testRequest.getTest());
            testQuestions.add(emptyTestQ);
            return new ResponseEntity<>(testQuestions, HttpStatus.OK);
        } else {
            Test test = testService.getTest(testRequest.getTestId());
            testRequest.testQuestion.setTest(test);
            //deal with variants
            StringTokenizer tokenizedVars = new StringTokenizer(testRequest.getVariant(), ";");
            List<String> readyVariants = new ArrayList<>();
            while (tokenizedVars.hasMoreElements()) {
                String currVar = tokenizedVars.nextElement().toString().trim();
                readyVariants.add(currVar);
            }
            String[] testVariants = new String[readyVariants.size()];
            testRequest.testQuestion.setQuestionVariants(readyVariants.toArray(testVariants));
            testService.saveQuestion(testRequest.testQuestion);
            if (testRequest.testQuestion.getComp() != null && !testRequest.testQuestion.getComp().isEmpty() &&
                    competencyRepository.findByCompetencyName(testRequest.testQuestion.getComp()) == null) {
                Competency comp = new Competency();
                comp.setCompetencyName(testRequest.testQuestion.getComp());
                competencyRepository.save(comp);
            }

            return new ResponseEntity<>(testService.getAllQuestionsByTest(test), HttpStatus.OK);
        }
    }

}
