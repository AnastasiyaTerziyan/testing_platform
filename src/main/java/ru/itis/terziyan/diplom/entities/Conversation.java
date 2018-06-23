package ru.itis.terziyan.diplom.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladislav Ulyanov on 22.05.17.
 * vk.com/etovladislav
 */
@Entity
@Table(name = "conversation")
public class Conversation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    @OneToMany
    private List<Message> messages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
    }
}
