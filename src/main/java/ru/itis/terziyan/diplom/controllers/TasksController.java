package ru.itis.terziyan.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.terziyan.diplom.entities.Test;
import ru.itis.terziyan.diplom.entities.TestQuestion;
import ru.itis.terziyan.diplom.repositories.TestQuestionRepository;
import ru.itis.terziyan.diplom.services.impl.UserGetService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks/")
public class TasksController {

    @Autowired
    private UserGetService userGetService;
    @Autowired
    private TestQuestionRepository testQuestionRepository;

    @GetMapping("/create/")
    public String createTaskPage(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        model.addAttribute("test", new Test());
        model.addAttribute("testQuestion", new TestQuestion());
        Set<String> competencies = testQuestionRepository.findAll().stream()
                .map(TestQuestion::getComp).collect(Collectors.toSet());
        model.addAttribute("comps", competencies);
        return "create_task";
    }


    @PostMapping("/create/")
    public String createTask(Model model, Test test, TestQuestion testQuestion,
                             @RequestParam(name = "variant") String variant) {
        StringTokenizer tokenizedIds = new StringTokenizer(variant, ";");
        List<String> variants = new ArrayList<>();
        while (tokenizedIds.hasMoreElements()) {
            String currVariant = tokenizedIds.nextElement().toString().trim();
            variants.add(currVariant);
        }
        System.out.println(test.getTestName()   );
        System.out.println(testQuestion.getAnswer());
        System.out.println(test.getTestCategory());
        System.out.println(testQuestion.getLevel());
        return "redirect:/";
    }
}
