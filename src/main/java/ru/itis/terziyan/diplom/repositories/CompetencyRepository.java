package ru.itis.terziyan.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.terziyan.diplom.entities.Competency;

@Repository
public interface CompetencyRepository extends JpaRepository<Competency, Long> {

    Competency findByCompetencyName(String competencyName);
}
