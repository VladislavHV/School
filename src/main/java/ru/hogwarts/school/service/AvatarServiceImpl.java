package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    @Value("${path.to.avatars.folder}")
    private String avatarFolder;

    public AvatarServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    @Transactional
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Files.createDirectories(Path.of(avatarFolder));
        Path filePath = Path.of(avatarFolder, student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        //byte[] fileData = avatarFile.getBytes();

        //String fileName = studentId + "_" + file.getOriginalFilename();
        //Path filePath = Paths.get(avatarFolder, fileName);
        //Files.createDirectories(filePath.getParent());
        //file.transferTo(filePath.toFile());

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateDataForDB(filePath));

        avatarRepository.save(avatar);
    }

    @Override
    public byte[] getAvatarFromDb(Long studentId) {
        return avatarRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Avatar nod found"))
                .getData();
    }

    @Override
    public byte[] getAvatarFromFile(Long studentId) throws IOException {
        Avatar avatar = avatarRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Avatar not found"));
        return Files.readAllBytes(Paths.get(avatar.getFilePath()));
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId)
                .orElse(new Avatar());
    }

    @Override
    public String getExtensions(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            throw new IllegalArgumentException("Файл не содержит расширения: " + fileName);
        }
        return fileName.substring(dotIndex + 1).toLowerCase();
    }

    @Override
    public byte[] generateDataForDB(Path filePath) throws IOException {
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0 ,0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }
}
