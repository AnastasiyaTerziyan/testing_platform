package ru.itis.terziyan.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "test_answer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestAnswer {
    @GenericGenerator(
            name = "testAnswerSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "TESTANSWER_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "100"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "testAnswerSequenceGenerator")
    private Long id;

    @Column(name = "user_answer")
    private String userAnswer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private TestQuestion testQuestion;

    private Boolean isCorrect;

    @JsonIgnore
    public void setTestQuestion(TestQuestion testQuestion) {
        this.testQuestion = testQuestion;
    }
}
