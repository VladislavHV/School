package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.nio.file.Path;

public interface AvatarService {
    void uploadAvatar(Long studentId, MultipartFile file) throws IOException;

    byte[] getAvatarFromDb(Long studentId);

    byte[] getAvatarFromFile(Long studentId) throws IOException;

    Avatar findAvatar(Long studentId);

    String getExtensions(String fileName);

    byte[] generateDataForDB(Path filePath) throws IOException;
}
