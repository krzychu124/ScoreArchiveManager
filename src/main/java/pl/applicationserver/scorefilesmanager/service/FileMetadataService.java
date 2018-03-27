package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.dto.SimpleFileInfo;

import java.util.List;

public interface FileMetadataService {
    boolean createFileMetadata(SimpleFileInfo fileInfo, String fileName, Long fileSize, String pathToDownload, String fileExtension);
    AbstractFileMetadata get(String fileName);

    List<AbstractFileMetadata> getByType(ScoreFileType fileType);
}
