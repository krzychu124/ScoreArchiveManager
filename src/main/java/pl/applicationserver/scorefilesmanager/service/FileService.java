package pl.applicationserver.scorefilesmanager.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.dto.DownloadedFile;
import pl.applicationserver.scorefilesmanager.dto.SimpleFileInfo;

import java.io.IOException;
import java.util.List;

public interface FileService {

    SAFileMetadata uploadFile(MultipartFile file, SimpleFileInfo fileInfo);

    Resource downloadFile(String fileName);
    DownloadedFile createDownloadedFile(String fileName);
    boolean removeFile(String fileName);

    List<String> getStorageFileListByFileType(ScoreFileType fileType);
    boolean generateThumbnail(String fileName);
    Boolean removeFilePermanently(Long id) throws IOException;
}
