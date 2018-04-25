package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.dto.DownloadedFile;
import pl.applicationserver.scorefilesmanager.dto.SimpleFileInfo;
import pl.applicationserver.scorefilesmanager.repository.SAFileMetadataRepository;
import pl.applicationserver.scorefilesmanager.service.*;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    //TODO logger
    private StorageService storageService;
    private FileMetadataService fileMetadataService;
    private SAFileMetadataRepository fileRepository;
    private ArchivedFileMetadataService archivedFileMetadataService;
    private ThumbnailCreatorService thumbnailCreatorService;
    private ScoreService scoreService;

    @Autowired
    public FileServiceImpl(StorageService storageService, FileMetadataService fileMetadataService, SAFileMetadataRepository fileRepository, ArchivedFileMetadataService archivedFileMetadataService, ThumbnailCreatorService thumbnailCreatorService, ScoreService scoreService) {
        this.storageService = storageService;
        this.fileMetadataService = fileMetadataService;
        this.fileRepository = fileRepository;
        this.archivedFileMetadataService = archivedFileMetadataService;
        this.thumbnailCreatorService = thumbnailCreatorService;
        this.scoreService = scoreService;
    }

    @Override
    public SAFileMetadata uploadFile(MultipartFile file, SimpleFileInfo fileInfo) {
        String generateFileName = generateFileName();
        String filePath = generateFilePath(fileInfo.getScoreFileType(), generateFileName);
        String pathToDownload = storageService.upload(file, filePath);
        String[] fileNameSplit = file.getOriginalFilename().split("\\.");
        String fileExtension = fileNameSplit[fileNameSplit.length - 1];
        String originalFileName = fileNameSplit[fileNameSplit.length -2];
        return fileMetadataService.createFileMetadata(fileInfo, generateFileName, originalFileName, file.getSize(), pathToDownload, fileExtension);
    }

    @Override
    public Resource downloadFile(String fileName) {
        SAFileMetadata file = fileRepository.getByFileName(fileName);
        try {
            byte[] content = storageService.download(file.getUrl());
            return new ByteArrayResource(content);
        } catch (Exception e) {
            System.out.print("[Exception] IO Exception message" + e.getMessage());
        }
        return null;
    }

    @Override
    public DownloadedFile createDownloadedFile(String fileName) {
        String fileInBase64 = downloadFileBase64(fileName);
        SAFileMetadata fileMetadata = fileMetadataService.get(fileName);
        if (fileInBase64 != null && fileMetadata != null) {
            return new DownloadedFile(fileInBase64, fileMetadata);
        }
        return null;
    }

    private String downloadFileBase64(String fileName) {
        SAFileMetadata file = fileRepository.getByFileName(fileName);
        try {
            byte[] content = storageService.download(file.getUrl());
            return Base64.getEncoder().encodeToString(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeFile(String fileName) {
        try {
            SAFileMetadata file = fileRepository.getByFileName(fileName);
            file.setDeleted(true);
            fileRepository.save(file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getStorageFileListByFileType(ScoreFileType fileType) {
        return storageService.getFileList(generateFilePath(fileType, ""));
    }

    @Override
    public boolean generateThumbnail(String fileName) {
        String file = downloadFileBase64(fileName);
        if (file != null) {
            String thumb = thumbnailCreatorService.createThumbnailBase64(Base64.getDecoder().decode(file));
            SAFileMetadata fileMetadata = fileRepository.getByFileName(fileName);
            fileMetadata.setThumbnail(thumb);
            fileRepository.save(fileMetadata);
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeFilePermanently(Long id) throws IOException {
        SAFileMetadata fileMetadata = fileRepository.getOne(id);
        try {
            byte[] content = storageService.download(fileMetadata.getUrl());
            archivedFileMetadataService.store(fileMetadata, content);
            System.out.println(fileMetadata.getFileName() + " file archive backup created");
            storageService.removePermanently(fileMetadata);
            System.out.println(fileMetadata.getFileName() + " removed permanently from storage");
        } catch (NoSuchFileException e) {
            System.out.println(e.getMessage() + " " + e.getCause());
        }
        scoreService.removePdfFromScore(id);
        fileRepository.deleteById(id);
        System.out.println("Metadata of file " + fileMetadata.getFileName() + " removed permanently");
        return true;
    }

    private String generateFileName() {
        return UUID.randomUUID().toString();
    }

    private String generateFilePath(ScoreFileType fileType, String fileName) {
        String folderPath = "/";
        switch (fileType) {
            case OTHER:
                folderPath = "/others/";
                break;
            case IMAGE:
                folderPath = "/scoreImages/";
                break;
            case MSCZ:
                folderPath = "/mscz/";
                break;
            case PDF:
                folderPath = "/pdf/";
                break;
        }
        return folderPath + fileName;
    }
}
