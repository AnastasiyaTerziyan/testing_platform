package ru.itis.terziyan.diplom.entities;

import javax.persistence.*;

/**
 * Created by Vladislav Ulyanov on 22.05.17.
 * vk.com/etovladislav
 */
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User from;

    @Column(name = "created_date")
    private Long createdDate;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
