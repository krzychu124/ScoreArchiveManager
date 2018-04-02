package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.service.StorageService;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("localStorage")
public class LocalTestStorageService implements StorageService{
    private final static String storageFolderPath = "C://ScoreArchiveStorage/";
    @Override
    public String upload(MultipartFile file, String filePath) {
        byte[] content = new byte[0];
        try {
            content = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(content.length>0) {
                Path path =Paths.get(storageFolderPath + filePath);
            try {
                if (Files.isRegularFile(path)) {
                    Files.delete(path);
                }
                Files.write(path, content, StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return path.toString();
        }
        return null;
    }

    @Override
    public byte[] download(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }
    @Override
    public List<String> getFileList(String path) {
        List<String> files = new ArrayList<>();
        try {
            Files.newDirectoryStream(Paths.get(path)).forEach(dir -> files.add(dir.getFileName().toString()));
            return files;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean removePermanently(SAFileMetadata fileMetadata) {
        if (Files.isRegularFile(Paths.get(fileMetadata.getUrl()))) {
            try {
                Files.delete(Paths.get(fileMetadata.getUrl()));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
