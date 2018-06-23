package ru.itis.terziyan.diplom.entities.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailForm {

    private List<String> group;
    private String from;
    private String subject;
    private String message;

}
