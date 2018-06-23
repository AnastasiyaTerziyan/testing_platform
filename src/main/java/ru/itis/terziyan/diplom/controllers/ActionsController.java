package ru.itis.terziyan.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.terziyan.diplom.entities.User;
import ru.itis.terziyan.diplom.entities.forms.MailForm;
import ru.itis.terziyan.diplom.services.MailClient;
import ru.itis.terziyan.diplom.services.impl.UserGetService;
import ru.itis.terziyan.diplom.services.interfaces.UserService;

import javax.servlet.http.HttpServletRequest;



@Controller
@RequestMapping("/action")
public class ActionsController {

    private MailClient mailClient;
    private UserService userService;
    private UserGetService userGetService;

    @Autowired
    private ActionsController(MailClient mailClient, UserService userService, UserGetService userGetService) {
        this.mailClient = mailClient;
        this.userService = userService;
        this.userGetService = userGetService;
    }

    @RequestMapping(value = "/decan/email/", method = RequestMethod.POST)
    public String emailController(Model model, @ModelAttribute MailForm mailForm) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        String midName = user.getMidName() == null ? "" : user.getMidName();
        mailForm.setFrom(user.getName() + " " + midName + " " + user.getLastName());
        try {
            mailClient.prepareAndSend(mailForm);
            model.addAttribute("status", "Письма разосланы");
        } catch (Exception e) {
            model.addAttribute("status", "Ошибка в отправке сообщений");
            e.printStackTrace();
        }
        model.addAttribute("title", "Почтовая рассылка");
        model.addAttribute("mailForm", new MailForm());
//        model.addAttribute("groups", groupService.getAllGroups());
        return "mail_form";
    }

    @RequestMapping(value = "/decan/email/", method = RequestMethod.GET)
    public String getEmailForm(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        model.addAttribute("title", "Почтовая рассылка");
        model.addAttribute("mailForm", new MailForm());
//        model.addAttribute("groups", groupService.getAllGroups());
        return "mail_form";
    }

    @RequestMapping(value = "/decan/ref_show/", method = RequestMethod.GET)
    public String getRefs(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
//        List<Reference> references = referenceService.getAll().stream()
//                .filter((ref) -> (!ref.isStatus()))
//                .collect(Collectors.toList());
//        model.addAttribute("refs", references);
        return "reference_show";
    }

    @RequestMapping(value = "/student/ref_show/", method = RequestMethod.GET)
    public String getRefsStudent(Model model) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
//        List<Reference> references = referenceService.getAll().stream()
//                .filter((ref) -> (ref.getUser().getEmail().equals(userGetService.getCurrentUser().getEmail())))
//                .collect(Collectors.toList());
//        model.addAttribute("refs", references);
        return "reference_show";
    }

    @RequestMapping(value = "/decan/ref_show/", method = RequestMethod.POST)
    public String deleteRefs(Model model, HttpServletRequest request) {
        model.addAttribute("user_info", userGetService.getCurrentUser());
        Long ref_id = Long.parseLong(request.getParameter("ref_id"));
//        referenceService.updateStatus(true, ref_id);
//        List<Reference> references = referenceService.getAll().stream()
//                .filter((ref) -> (!ref.isStatus()))
//                .collect(Collectors.toList());
//        model.addAttribute("refs", references);
        return "reference_show";
    }

    @RequestMapping(value = "/decan/issue_show/", method = RequestMethod.POST)
    public String deleteIssues(Model model, HttpServletRequest request){
        Long iss_id = Long.parseLong(request.getParameter("iss_id"));
        String type = request.getParameter("type");
        if (type.equals("hack")){
//            userHackService.delete(iss_id);
        } else {
//            userInternshipService.delete(iss_id);
        }
        return "redirect:/userprofile/issues/";
    }
}
