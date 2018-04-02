package pl.applicationserver.scorefilesmanager.service;

import org.springframework.web.multipart.MultipartFile;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;

import java.io.IOException;
import java.util.List;

public interface StorageService {

    String upload(MultipartFile file, String path);
    byte[] download(String filePath) throws IOException;
    List<String> getFileList(String path);

    boolean removePermanently(SAFileMetadata fileMetadata);
}
