package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.*;
import pl.applicationserver.scorefilesmanager.dto.SimpleFileInfo;
import pl.applicationserver.scorefilesmanager.repository.SAFileMetadataRepository;
import pl.applicationserver.scorefilesmanager.service.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class FileMetadataServiceImpl implements FileMetadataService {
    private ScoreTitleService scoreTitleService;
    private InstrumentService instrumentService;
    private ScoreTypeService scoreTypeService;
    private SAFileMetadataRepository fileRepository;

    @Autowired
    public FileMetadataServiceImpl(SAFileMetadataRepository fileRepository, ScoreTitleService scoreTitleService, InstrumentService instrumentService, ScoreTypeService scoreTypeService) {
        this.fileRepository = fileRepository;
        this.scoreTitleService = scoreTitleService;
        this.instrumentService = instrumentService;
        this.scoreTypeService = scoreTypeService;
    }

    @Override
    public SAFileMetadata createFileMetadata(SimpleFileInfo fileInfo, String fileName, Long fileSize, String pathToDownload, String fileExtension) {
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
        SAFileMetadata fileMetadata = null;
        switch (fileInfo.getScoreFileType()) {
            case PDF:
                fileMetadata = new SAFileMetadata(fileName, scoreTitle, scoreType, instrument, pathToDownload, fileSize, "pdf", fileType);
                break;
            case MSCZ:
                fileMetadata = new SAFileMetadata(fileName, scoreTitle, scoreType, instrument, pathToDownload, fileSize, "mscz", fileType);
                break;
            case IMAGE:
                fileMetadata = new SAFileMetadata(fileName, scoreTitle, scoreType, instrument, pathToDownload, fileSize, fileExtension, fileType);
                break;
            case OTHER:
                fileMetadata = new SAFileMetadata(fileName, scoreTitle, scoreType, instrument, pathToDownload, fileSize, fileExtension, fileType);
                break;
        }
        return fileRepository.save(fileMetadata);
    }

    @Override
    public SAFileMetadata get(String fileName) {
        return fileRepository.getByFileName(fileName);
    }

    @Override
    public List<SAFileMetadata> getByType(ScoreFileType fileType) {
        return fileRepository.getAllByScoreFileType(fileType);
    }

    @Override
    public List<SAFileMetadata> getFilesWithoutThumb(ScoreFileType fileType) {
        return fileRepository.getAllByScoreFileTypeAndThumbnailIsNull(fileType);
    }

    @Override
    public List<SAFileMetadata> getAllByInstrument(Long instrumentId) {
        return fileRepository.getAllByInstrumentId(instrumentId);
    }
}
