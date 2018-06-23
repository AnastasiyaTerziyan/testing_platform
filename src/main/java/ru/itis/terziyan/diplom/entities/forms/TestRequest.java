package ru.itis.terziyan.diplom.entities.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itis.terziyan.diplom.entities.Test;
import ru.itis.terziyan.diplom.entities.TestQuestion;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestRequest {

    public Test test;
    public TestQuestion testQuestion;
    String variant;
    Long testId;

}
