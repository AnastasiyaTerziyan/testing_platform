package ru.itis.terziyan.diplom.controllers;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.terziyan.diplom.entities.Materials;
import ru.itis.terziyan.diplom.services.interfaces.MaterialsService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    @Autowired
    private MaterialsService materialsService;

    @RequestMapping(value = "/materials/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("filename") String filename,
                                   @RequestParam("file") MultipartFile file, Materials materials,
                                   RedirectAttributes redirectAttributes) {


        String newFileName = materialsService.cyr2lat(filename);
        Path path = Paths.get(URI.create("file:///opt/icatz/files/" + newFileName));
        System.out.println(path);
        if (!file.isEmpty() && materialsService.getMaterials(newFileName) == null) {
            try {
                File loadFile = new File(path.toString());
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(loadFile));
                stream.write(bytes);
                stream.close();
                System.out.println(loadFile.getAbsolutePath());
                materials.setFilename(newFileName);
                materialsService.add(materials);
                return "redirect:/materials/";
            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addAttribute("error", "Ошибка в обработке файла, возможно он не поддерживается");
                return "redirect:/materials/";
            }
        }
        redirectAttributes.addAttribute("error", "Файл не поддерживается либо уже существует");
        return "redirect:/materials/";
    }

    @RequestMapping(value = "/materials/{path}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getFile(@PathVariable("path") String path) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
        File file = FileUtils.getFile(path);
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/files/{filename:.+}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable("filename") String fileName,
            HttpServletResponse response) throws IOException {
        System.out.println(fileName);
        String newFileName = fileName.replaceAll(" ", "_");
        File file = new File(URI.create("file:///opt/icatz/files/" + newFileName));
        FileInputStream fileIn = new FileInputStream(file);
        response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(newFileName)));
        ServletOutputStream out = response.getOutputStream();
        byte[] outputByte = new byte[4096];
        while (fileIn.read(outputByte, 0, 4096) != -1) {
            out.write(outputByte, 0, 4096);
        }
        fileIn.close();
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/labs/{filename:.+}", method = RequestMethod.GET) // в нужный контроллер сунуть
    public void getPresentation(
            @PathVariable("filename") String fileName,
            HttpServletResponse response) throws IOException {
        String newFileName = fileName.replaceAll(" ", "_"); //тут тоже, если презы без пробелов в названии то не нужно
        File file = new File(URI.create("file:///opt/icatz/files/" + newFileName)); //тут нужен путь до папки през
        FileInputStream fileIn = new FileInputStream(file);
        response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(newFileName)));
        ServletOutputStream out = response.getOutputStream();
        byte[] outputByte = new byte[4096];
        while (fileIn.read(outputByte, 0, 4096) != -1) {
            out.write(outputByte, 0, 4096);
        }
        fileIn.close();
        out.flush();
        out.close();
    }



}