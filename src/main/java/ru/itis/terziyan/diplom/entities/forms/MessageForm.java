package ru.itis.terziyan.diplom.entities.forms;

/**
 * Created by Vladislav Ulyanov on 23.05.17.
 * vk.com/etovladislav
 */
public class MessageForm {

    private Long fromId;
    private String message;

    public MessageForm(Long fromId, String message) {
        this.fromId = fromId;
        this.message = message;
    }

    public MessageForm() {
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
