package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadataArchive;

import java.util.List;

public interface ArchivedFileMetadataService {

    boolean store(AbstractFileMetadata fileMetadata, byte[] content);
    AbstractFileMetadataArchive getByFileName(String fileName);
    List<AbstractFileMetadataArchive> getAll();
}
