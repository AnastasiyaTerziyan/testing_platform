package ru.itis.terziyan.diplom.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.terziyan.diplom.entities.Materials;
import ru.itis.terziyan.diplom.repositories.MaterialsRepository;
import ru.itis.terziyan.diplom.services.interfaces.MaterialsService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 1 on 28.05.2017.
 */
@Service
public class MaterialsServiceImpl implements MaterialsService {

    @Autowired
    private MaterialsRepository materialsRepository;

    @Autowired
    private UserGetService userGetService;

    @Override
    public Materials add(Materials materials) {
        materials.setUser(userGetService.getCurrentUser());
        Materials save = materialsRepository.save(materials);
        return save;
    }

    @Override
    public String getFileExtension(String filename) {
        int index = filename.indexOf('.');
        return index == -1? null : filename.substring(index);
    }

    @Override
    public void delete(Long id) {
        materialsRepository.delete(id);

    }

    @Override
    public List<Materials> findAll() {
        return materialsRepository.findAll();
    }

    @Override
    @Transactional
    public List<Materials> getAllByCourseAndSemesterAndSubject(Integer course, Integer semester, String subject) {
        return materialsRepository.getAllByCourseAndSemesterAndSubject(course, semester, subject);
    }

    @Override
    @Transactional
    public Materials getMaterials(String filename) {
        return  materialsRepository.getMaterialsByFilename(filename);
    }

    private String cyr2latChar(char ch){
        switch (ch){
            case 'А': return "A";
            case 'а': return "a";
            case 'Б': return "B";
            case 'б': return "b";
            case 'В': return "V";
            case 'в': return "v";
            case 'Г': return "G";
            case 'г': return "g";
            case 'Д': return "D";
            case 'д': return "d";
            case 'Е': return "E";
            case 'е': return "e";
            case 'Ё': return "JE";
            case 'ё': return "je";
            case 'Ж': return "ZH";
            case 'ж': return "zh";
            case 'З': return "Z";
            case 'з': return "z";
            case 'И': return "I";
            case 'и': return "i";
            case 'Й': return "Y";
            case 'й': return "y";
            case 'К': return "K";
            case 'к': return "k";
            case 'Л': return "L";
            case 'л': return "l";
            case 'М': return "M";
            case 'м': return "m";
            case 'Н': return "N";
            case 'н': return "n";
            case 'О': return "O";
            case 'о': return "o";
            case 'П': return "P";
            case 'п': return "p";
            case 'Р': return "R";
            case 'р': return "r";
            case 'С': return "S";
            case 'с': return "s";
            case 'Т': return "T";
            case 'т': return "t";
            case 'У': return "U";
            case 'у': return "u";
            case 'Ф': return "F";
            case 'ф': return "f";
            case 'Х': return "KH";
            case 'х': return "kh";
            case 'Ц': return "C";
            case 'ц': return "c";
            case 'Ч': return "CH";
            case 'ч': return "ch";
            case 'Ш': return "SH";
            case 'ш': return "sh";
            case 'Щ': return "JSH";
            case 'щ': return "jsh";
            case 'Ъ': return "HH";
            case 'ъ': return "hh";
            case 'Ы': return "IH";
            case 'ы': return "ih";
            case 'Ь': return "JH";
            case 'ь': return "jh";
            case 'Э': return "EH";
            case 'э': return "eh";
            case 'Ю': return "JU";
            case 'ю': return "ju";
            case 'Я': return "JA";
            case 'я': return "ja";
            case ' ': return "_";
            case ',': return "";

            default: return String.valueOf(ch);
        }
    }

    @Override
    public String cyr2lat(String s){
        StringBuilder sb = new StringBuilder(s.length()*2);

        for(char ch: s.toCharArray()){
            sb.append(cyr2latChar(ch));
        }
        return sb.toString();
    }
}
