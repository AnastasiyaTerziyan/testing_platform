package ru.itis.terziyan.diplom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import ru.itis.terziyan.diplom.entities.forms.MailForm;
import ru.itis.terziyan.diplom.services.interfaces.UserService;



@Service
public class MailClient {

    private JavaMailSender mailSender;
    private MailContentBuilder contentBuilder;
    private UserService userService;

    @Autowired
    public MailClient(JavaMailSender mailSender, MailContentBuilder contentBuilder, UserService userService) {
        this.mailSender = mailSender;
        this.contentBuilder = contentBuilder;
        this.userService = userService;
    }

    public void prepareAndSendSupport(String subject, String message, String from) throws MailException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("testcps24@gmail.com");
            mimeMessageHelper.setTo("unlimited54321@mail.ru");
            mimeMessageHelper.setSubject("iCatz форма поддержки: " + subject);
            String content = contentBuilder.buildSupport(subject, message, from);
            mimeMessageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }

    public void prepareAndSend(MailForm mailForm) throws Exception {
//        List<User> usersToSend = userService.findByGroupIn(mailForm.getGroup());
//        List<String> recipients = new ArrayList<>();
//        usersToSend.forEach(user -> recipients.add(user.getEmail()));
//        MimeMessagePreparator messagePreparator = mimeMessage -> {
//            String[] recipientsArray = new String[recipients.size()];
//            recipientsArray = recipients.toArray(recipientsArray);
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom("testcps24@gmail.com");
//            messageHelper.setTo(recipientsArray);
//            messageHelper.setSubject("iCatz оповещение " + mailForm.getSubject());
//            String content = contentBuilder.build(mailForm);
//            messageHelper.setText(content, true);
//        };
//        try {
//            mailSender.send(messagePreparator);
//        } catch (MailException e) {
//            e.printStackTrace();
//        }
    }
}
