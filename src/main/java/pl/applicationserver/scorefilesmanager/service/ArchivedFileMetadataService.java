package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.domain.ArchivedFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;

import java.util.List;

public interface ArchivedFileMetadataService {

    boolean store(SAFileMetadata fileMetadata, byte[] content);
    ArchivedFileMetadata getByFileName(String fileName);
    List<ArchivedFileMetadata> getAll();
}
