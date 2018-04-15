package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.dto.SimpleFileInfo;

import java.util.List;

public interface FileMetadataService {
    SAFileMetadata createFileMetadata(SimpleFileInfo fileInfo, String fileName, Long fileSize, String pathToDownload, String fileExtension);
    SAFileMetadata get(String fileName);
    List<SAFileMetadata> getByType(ScoreFileType fileType);
    List<SAFileMetadata> getByTypeOrchestra(ScoreFileType fileType);
    List<SAFileMetadata> getByTypeJBBand(ScoreFileType fileType);
    List<SAFileMetadata> getFilesWithoutThumb(ScoreFileType fileType);
    List<SAFileMetadata> getAllByInstrument(Long instrumentId);
}
