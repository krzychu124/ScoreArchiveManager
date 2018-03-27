package pl.applicationserver.scorefilesmanager.service;

import org.springframework.web.multipart.MultipartFile;
import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadata;

import java.util.List;

public interface StorageService {

    String upload(MultipartFile file, String path);
    byte[] download(String filePath);
    List<String> getFileList(String path);

    boolean removePermanently(AbstractFileMetadata fileMetadata);
}
