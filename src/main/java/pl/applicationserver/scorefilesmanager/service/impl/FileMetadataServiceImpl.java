package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.*;
import pl.applicationserver.scorefilesmanager.dto.SimleSAFileMetadata;
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
    private static final String NO_TITLE = "Brak";
    private static final String MANY_INSTRUMENTS = "Różne";

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
            if (scoreTitleService.exists(fileInfo.getTitleId())) {
                scoreTitle = scoreTitleService.getTitle(fileInfo.getTitleId());
            } else {
                scoreTitle = scoreTitleService.getByName(NO_TITLE);
            }
            if(instrumentService.exists(fileInfo.getInstrumentId())) {
                instrument = instrumentService.getInstrument(fileInfo.getInstrumentId());
            } else {
                instrument = instrumentService.getInstrument(MANY_INSTRUMENTS,Byte.parseByte("99"));
            }
            if(scoreTypeService.exists(fileInfo.getScoreTypeId())) {
                scoreType = scoreTypeService.getScoreType(fileInfo.getScoreTypeId());
            } else {
                scoreType = scoreTypeService.getScoreType(1L);
            }
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
    public List<SAFileMetadata> getByTypeOrchestra(ScoreFileType fileType) {
        return fileRepository.getAllByScoreFileType(fileType);
    }

    @Override
    public List<SAFileMetadata> getByTypeJBBand(ScoreFileType fileType) {
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

    @Override
    public SimleSAFileMetadata getSimpleByFileName(String name) {
        return fileRepository.getSimpleByFileName(name);
    }

    @Override
    public List<SimleSAFileMetadata> getAllSimpleByScoreFileType(ScoreFileType fileType) {
        return fileRepository.getAllSimpleByScoreFileType(fileType);
    }

    @Override
    public List<SimleSAFileMetadata> getAllSimpleByScoreType(ScoreType scoreType) {
        return fileRepository.getAllSimpleByScoreType(scoreType);
    }

    @Override
    public List<SimleSAFileMetadata> getAllSimpleByInstrumentId(Long instrumentId) {
        return fileRepository.getAllSimpleByInstrumentId(instrumentId);
    }

    @Override
    public List<SimleSAFileMetadata> getAllSimpleNotDeleted() {
        return fileRepository.getAllSimpleNotDeleted();
    }
}
