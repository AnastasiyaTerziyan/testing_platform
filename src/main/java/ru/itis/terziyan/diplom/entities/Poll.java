package ru.itis.terziyan.diplom.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by unlim_000 on 02.06.2017.
 */

@Entity
@Table(name = "polls")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @Column(name = "for_whom")
    private String forWhom;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
