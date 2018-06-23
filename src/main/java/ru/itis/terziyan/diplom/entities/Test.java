package ru.itis.terziyan.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.itis.terziyan.diplom.entities.enums.TestCategory;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "test")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Test {
    @GenericGenerator(
            name = "testSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "TEST_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "100"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "testSequenceGenerator")
    private Long id;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "test_category")
    private TestCategory testCategory;

    @Column(name = "test_comp")
    private String testComp;


    @OneToMany
    @JoinColumn(name = "test_id")
    Set<TestQuestion> testQuestions;

    @JsonIgnore
    public void setTestQuestions(Set<TestQuestion> testQuestions) {
        this.testQuestions = testQuestions;
    }
}
