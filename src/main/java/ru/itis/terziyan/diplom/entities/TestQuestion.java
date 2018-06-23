package ru.itis.terziyan.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.itis.terziyan.diplom.entities.enums.QuestionType;
import ru.itis.terziyan.diplom.entities.enums.TestLevel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "test_question")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestQuestion {
    @GenericGenerator(
            name = "testQuestionSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "TESTQUESTION_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "100"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "testQuestionSequenceGenerator")
    private Long id;

    @Column(name = "question_text", length = 10000)
    private String questionText;

    @Column(name = "question_variants")
    private String[] questionVariants;

    @Column(name = "comp")
    private String comp;

    @Column(name = "answer")
    private String answer;

    @Column(name = "level")
    private TestLevel level;

    @Column(name = "question_type")
    private QuestionType questionType;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToMany
    @JoinColumn(name = "question_id")
    private Set<TestAnswer> testAnswers;

    @JsonIgnore
    public Set<TestAnswer> getTestAnswers() {
        return testAnswers;
    }
}
