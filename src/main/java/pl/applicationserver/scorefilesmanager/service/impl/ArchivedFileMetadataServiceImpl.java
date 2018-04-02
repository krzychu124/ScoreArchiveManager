package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.ArchivedFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.repository.ArchivedFileMetadataRepository;
import pl.applicationserver.scorefilesmanager.service.ArchivedFileMetadataService;

import java.util.List;

@Service
public class ArchivedFileMetadataServiceImpl implements ArchivedFileMetadataService {
    private ArchivedFileMetadataRepository fileArchiveRepository;

    @Autowired
    public ArchivedFileMetadataServiceImpl(ArchivedFileMetadataRepository fileMetadataRepository) {
        this.fileArchiveRepository = fileMetadataRepository;
    }

    @Override
    public boolean store(SAFileMetadata fileMetadata, byte[] content) {
        ArchivedFileMetadata a = new ArchivedFileMetadata(fileMetadata);
        a.setContent(content);
        ArchivedFileMetadata archive = fileArchiveRepository.save(a);
       return archive.getId() != null;
    }

    @Override
    public ArchivedFileMetadata getByFileName(String fileName) {
        return fileArchiveRepository.getByFileName(fileName);
    }

    @Override
    public List<ArchivedFileMetadata> getAll() {
        return fileArchiveRepository.findAll();
    }


}
