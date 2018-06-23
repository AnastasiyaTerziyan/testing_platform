package ru.itis.terziyan.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "competency")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Competency {

    @Id
    @GeneratedValue
    private Long id;

    private String competencyName;

    @OneToMany(mappedBy = "competency")
    private Set<UserCompetency> userCompetencies;
}
