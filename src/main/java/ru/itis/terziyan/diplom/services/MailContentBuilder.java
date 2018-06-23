package ru.itis.terziyan.diplom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.itis.terziyan.diplom.entities.forms.MailForm;



@Service
public class MailContentBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(MailForm mailForm) {
        Context context = new Context();
        context.setVariable("from", mailForm.getFrom());
        context.setVariable("subject", mailForm.getSubject());
        context.setVariable("message", mailForm.getMessage());
        return templateEngine.process("mailTemplate", context);
    }

    public String buildSupport(String subject, String message, String from) {
        Context context = new Context();
        context.setVariable("from", from);
        context.setVariable("subject", subject);
        context.setVariable("message", message);
        return templateEngine.process("supportTemplate", context);
    }
}
