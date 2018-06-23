package ru.itis.terziyan.diplom.services.interfaces;

import ru.itis.terziyan.diplom.entities.Conversation;
import ru.itis.terziyan.diplom.entities.Message;
import ru.itis.terziyan.diplom.entities.User;
import ru.itis.terziyan.diplom.entities.forms.MessageForm;

import java.util.List;

/**
 * Created by Vladislav Ulyanov on 23.05.17.
 * vk.com/etovladislav
 */
public interface MessageService {
    Message saveMessage(Long to, MessageForm message);

    List<Conversation> getConversation(User user);

    List<Message> getMessages(Long id);

}
