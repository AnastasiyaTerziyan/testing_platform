package ru.itis.terziyan.diplom.services.interfaces;

import ru.itis.terziyan.diplom.entities.Materials;

import java.util.List;

/**
 * Created by 1 on 28.05.2017.
 */
public interface MaterialsService {
    Materials add(Materials materials);

    void delete(Long id);
    String getFileExtension(String filename);
    List<Materials> findAll();
    List<Materials> getAllByCourseAndSemesterAndSubject(Integer course, Integer semester, String subject);
    Materials getMaterials(String filename);
    String cyr2lat(String filename);
}
