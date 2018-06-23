package ru.itis.terziyan.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.terziyan.diplom.entities.Materials;

import java.util.List;


public interface MaterialsRepository extends JpaRepository<Materials, Long> {

    List<Materials> getAllByCourseAndSemesterAndSubject(Integer course, Integer semester, String subject);
    Materials getMaterialsByFilename(String filename);
}
