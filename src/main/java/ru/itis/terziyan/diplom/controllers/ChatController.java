package ru.itis.terziyan.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itis.terziyan.diplom.entities.Conversation;
import ru.itis.terziyan.diplom.entities.Message;
import ru.itis.terziyan.diplom.entities.User;
import ru.itis.terziyan.diplom.entities.forms.MessageForm;
import ru.itis.terziyan.diplom.entities.forms.OutputMessage;
import ru.itis.terziyan.diplom.entities.forms.SearchUserDto;
import ru.itis.terziyan.diplom.services.interfaces.MessageService;
import ru.itis.terziyan.diplom.services.interfaces.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Vladislav Ulyanov on 23.05.17.
 * vk.com/etovladislav
 */
@RestController
public class ChatController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;


    @MessageMapping("/chat/{to}")
    @SendTo("/topic/messages/{to}")
    public OutputMessage send(@DestinationVariable Long to, @RequestBody MessageForm messageForm, Principal principal) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(principal.getName());
        Message message = messageService.saveMessage(to, messageForm);
        return new OutputMessage(user, messageForm.getMessage(), message.getCreatedDate());
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public List<Conversation> messages() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        return messageService.getConversation(user);
    }

    @RequestMapping(value = "/user_info", method = RequestMethod.GET)
    public User userInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        return user;
    }

    @RequestMapping(value = "/conf/{id}", method = RequestMethod.GET)
    public List<Message> getMessages(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        List<Message> messages = messageService.getMessages(id);
        Collections.sort(messages, (p1, p2) -> p1.getCreatedDate().compareTo(p2.getCreatedDate()));
        return messageService.getMessages(id);
    }

    @RequestMapping(value = "/search_user", method = RequestMethod.GET)
    public List<SearchUserDto> searchUser(@RequestParam("term") String term) {
        List<SearchUserDto> list = new ArrayList<>();
        List<User> users = userService.findUserByEmailLike(term);
        list.addAll(users.stream().map(user -> new SearchUserDto(1l, user.getEmail(), user.getEmail(), user)).collect(Collectors.toList()));
        return list;
    }
}
