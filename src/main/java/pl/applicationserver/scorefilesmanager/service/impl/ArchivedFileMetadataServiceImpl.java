package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadataArchive;
import pl.applicationserver.scorefilesmanager.domain.ArchivedFileMetadata;
import pl.applicationserver.scorefilesmanager.repository.AbstractFileArchiveRepository;
import pl.applicationserver.scorefilesmanager.service.ArchivedFileMetadataService;

import java.util.List;

@Service
public class ArchivedFileMetadataServiceImpl implements ArchivedFileMetadataService {
    private AbstractFileArchiveRepository abstractFileArchiveRepository;

    @Autowired
    public ArchivedFileMetadataServiceImpl(AbstractFileArchiveRepository abstractFileArchiveRepository) {
        this.abstractFileArchiveRepository = abstractFileArchiveRepository;
    }

    @Override
    public boolean store(AbstractFileMetadata fileMetadata, byte[] content) {
        AbstractFileMetadataArchive a = new ArchivedFileMetadata(fileMetadata);
        a.setContent(content);
       AbstractFileMetadataArchive archive = abstractFileArchiveRepository.save(a);
       return archive.getId() != null;
    }

    @Override
    public AbstractFileMetadataArchive getByFileName(String fileName) {
        return abstractFileArchiveRepository.getByFileName(fileName);
    }

    @Override
    public List<AbstractFileMetadataArchive> getAll() {
        return abstractFileArchiveRepository.findAll();
    }


}
