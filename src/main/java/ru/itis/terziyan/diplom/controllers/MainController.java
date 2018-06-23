package ru.itis.terziyan.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.terziyan.diplom.entities.forms.MailForm;
import ru.itis.terziyan.diplom.services.impl.UserGetService;
import ru.itis.terziyan.diplom.services.interfaces.MaterialsService;
import ru.itis.terziyan.diplom.services.interfaces.PollsService;

/**
 * Created by unlim_000 on 10.05.2017.
 */

@Controller
@RequestMapping("/")
public class MainController {


    @Autowired
    private MaterialsService materialsService;


    @Autowired
    private UserGetService userGetService;

    @Autowired
    private PollsService pollsService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Главная");
//        List<News> topNews = new ArrayList<>();
//        List<News> bottomNews = new ArrayList<>();
//        List<News> facNews = newsService.findAllByType("fac");
//        List<News> itNews = newsService.findAllByType("it");
//        for (int i = 1; i < 4; i++) {
//            topNews.add(facNews.get(i));
//            bottomNews.add(itNews.get(i));
//        }
        model.addAttribute("user_info", userGetService.getCurrentUser());
//        model.addAttribute("first_it", itNews.get(0));
//        model.addAttribute("first_fac", facNews.get(0));
//        model.addAttribute("news_up", topNews);
//        model.addAttribute("news_down", bottomNews);
        model.addAttribute("mailForm", new MailForm());
        return "index";
    }


    @RequestMapping("/calendar/")
    public String calendar(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
//        model.addAttribute("events", eventService.getAllForWeek().entrySet());
        model.addAttribute("title", "Календарь событий");
        return "calendar";
    }

    @RequestMapping("/materials/")
    public String materials(Model model, @ModelAttribute("error") String errorString) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        model.addAttribute("title", "Учебные материалы");
        model.addAttribute("materialsList", materialsService.findAll());
        if (errorString != null && !errorString.isEmpty()){
            model.addAttribute("error", errorString);
        }
        return "materials";
    }

    @RequestMapping("/interview/{who}")
    public String interview(Model model, @PathVariable String who) {
        if (!who.equals("all")) {
            model.addAttribute("polls", pollsService.getByForWhom(who));
        } else {
            model.addAttribute("polls", pollsService.getAll());
        }
        model.addAttribute("user_info", userGetService.getCurrentUser());
        model.addAttribute("title", "Опросы");
        return "interview";
    }

    @RequestMapping("/support/")
    public String support(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        model.addAttribute("title", "Поддержка");
        return "support";
    }

    @RequestMapping("/morenews/{type}")
    public String moreNews(Model model, @PathVariable String type) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
//        model.addAttribute("news", newsService.findAllByType(type));
        model.addAttribute("title", "Новости");
        return "more_news";
    }

    @RequestMapping("/new/{id}")
    public String oneNew(Model model, @PathVariable Long id) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
//        model.addAttribute("news", newsService.findById(id));
        model.addAttribute("title", "Новости");
        return "post";
    }
}
