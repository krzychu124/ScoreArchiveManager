package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.*;
import pl.applicationserver.scorefilesmanager.dto.SimpleFileInfo;
import pl.applicationserver.scorefilesmanager.repository.AbstractFileRepository;
import pl.applicationserver.scorefilesmanager.service.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class FileMetadataServiceImpl implements FileMetadataService {
    private ScoreTitleService scoreTitleService;
    private InstrumentService instrumentService;
    private ScoreTypeService scoreTypeService;
    private AbstractFileRepository fileRepository;

    @Autowired
    public FileMetadataServiceImpl(AbstractFileRepository fileRepository, ScoreTitleService scoreTitleService, InstrumentService instrumentService, ScoreTypeService scoreTypeService) {
        this.fileRepository = fileRepository;
        this.scoreTitleService = scoreTitleService;
        this.instrumentService = instrumentService;
        this.scoreTypeService = scoreTypeService;
    }

    @Override
    public AbstractFileMetadata createFileMetadata(SimpleFileInfo fileInfo, String fileName, Long fileSize, String pathToDownload, String fileExtension) {
        ScoreTitle scoreTitle;
        Instrument instrument;
        ScoreType scoreType;
        try {
            scoreTitle = scoreTitleService.getTitle(fileInfo.getTitleId());
            instrument = instrumentService.getInstrument(fileInfo.getInstrumentId());
            scoreType = scoreTypeService.getScoreType(fileInfo.getScoreTypeId());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        ScoreFileType fileType = fileInfo.getScoreFileType();
        AbstractFileMetadata fileMetadata = null;
        switch (fileInfo.getScoreFileType()) {
            case PDF:
                fileMetadata = new PdfFileMetadata(fileName, scoreTitle, scoreType, instrument, pathToDownload, fileSize, "pdf", fileType);
                break;
            case MSCZ:
                fileMetadata = new MuseScoreFileMetadata(fileName, scoreTitle, scoreType, instrument, pathToDownload, fileSize, "mscz", fileType);
                break;
            case IMAGE:
                fileMetadata = new ImageFileMetadata(fileName, scoreTitle, scoreType, instrument, pathToDownload, fileSize, fileExtension, fileType);
                break;
            case OTHER:
                fileMetadata = new OtherFileMetadata(fileName, scoreTitle, scoreType, instrument, pathToDownload, fileSize, fileExtension, fileType);
                break;
        }
        fileRepository.save(fileMetadata);
        return fileMetadata;
    }

    @Override
    public AbstractFileMetadata get(String fileName) {
        return fileRepository.getByFileName(fileName);
    }

    @Override
    public List<AbstractFileMetadata> getByType(ScoreFileType fileType) {
        return fileRepository.getAllByScoreFileType(fileType);
    }
}
