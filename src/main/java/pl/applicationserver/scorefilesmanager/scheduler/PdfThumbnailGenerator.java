package pl.applicationserver.scorefilesmanager.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.service.FileMetadataService;
import pl.applicationserver.scorefilesmanager.service.FileService;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableAsync
@EnableScheduling
public class PdfThumbnailGenerator {
    private Logger logger = LogManager.getLogger(PdfThumbnailGenerator.class);
    private FileService fileService;
    private FileMetadataService fileMetadataService;
    private final List<SAFileMetadata> filesToGenerate = new ArrayList<>();

    @Autowired
    public PdfThumbnailGenerator(FileService fileService, FileMetadataService fileMetadataService) {
        this.fileService = fileService;
        this.fileMetadataService = fileMetadataService;
    }

    public void addToGeneratorTask(SAFileMetadata fileMetadata) {
        if(fileMetadata.getScoreFileType().equals(ScoreFileType.PDF)) {
            filesToGenerate.add(fileMetadata);
        }
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    protected void generateThumbnails(){
        logger.info("Scheduled pdf thumbnail generator");
        List<SAFileMetadata> copiedList;
        synchronized (filesToGenerate) {
            copiedList = new ArrayList<>(filesToGenerate);
            filesToGenerate.clear();
        }
        while (copiedList.size()>0) {
            generateAndSave(copiedList.get(0));
            copiedList.remove(0);
        }
    }

    public void findFilesToGenerateThumbs(){
        this.filesToGenerate.addAll(fileMetadataService.getFilesWithoutThumb(ScoreFileType.PDF));
        logger.info("Added " + filesToGenerate.size() + " files to thumbnail generator task");
    }

    @Async
    protected void generateAndSave(SAFileMetadata fileMetadata) {
        fileService.generateThumbnail(fileMetadata.getFileName());
        logger.info("Generated thumb for fileName "+ fileMetadata.getFileName());
    }
}
