package ru.itis.terziyan.diplom.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import ru.itis.terziyan.diplom.entities.Conversation;
import ru.itis.terziyan.diplom.entities.Message;
import ru.itis.terziyan.diplom.entities.User;
import ru.itis.terziyan.diplom.entities.forms.MessageForm;
import ru.itis.terziyan.diplom.repositories.ConversationRepository;
import ru.itis.terziyan.diplom.repositories.MessageRepository;
import ru.itis.terziyan.diplom.repositories.UserRepository;
import ru.itis.terziyan.diplom.services.interfaces.MessageService;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vladislav Ulyanov on 23.05.17.
 * vk.com/etovladislav
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConversationRepository conversationRepository;

    @Override
    @Transactional
    public Message saveMessage(Long to, MessageForm messageFrom) {
        User uFrom = userRepository.findOne(messageFrom.getFromId());
        User uTo = userRepository.findOne(to);
        Conversation conversation = conversationRepository.findConversation(uFrom.getId(), uTo.getId());
        if (conversation == null) {
            conversation = new Conversation();
            conversation.setUser1(uFrom);
            conversation.setUser2(uTo);
        }
        Message message = new Message();
        message.setCreatedDate(System.currentTimeMillis());
        message.setMessage(HtmlUtils.htmlEscape(messageFrom.getMessage()));
        message.setFrom(uFrom);
        messageRepository.save(message);
        conversation.addMessage(message);
        conversationRepository.save(conversation);
        return message;
    }

    @Override
    public List<Conversation> getConversation(User user) {
        List<Conversation> conversations = conversationRepository.findConversationByUserId(user.getId());
        Collections.sort(conversations, (p1, p2) -> p1.getMessages().get(p1.getMessages().size() - 1).getCreatedDate().compareTo(p2.getMessages().get(p2.getMessages().size() - 1).getCreatedDate()));
        Collections.reverse(conversations);
        return conversations;
    }

    @Override
    public List<Message> getMessages(Long id) {
        return conversationRepository.findOne(id).getMessages();
    }
}
