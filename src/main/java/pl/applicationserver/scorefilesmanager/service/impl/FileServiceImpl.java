package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.dto.DownloadedFile;
import pl.applicationserver.scorefilesmanager.dto.SimpleFileInfo;
import pl.applicationserver.scorefilesmanager.repository.AbstractFileRepository;
import pl.applicationserver.scorefilesmanager.service.ArchivedFileMetadataService;
import pl.applicationserver.scorefilesmanager.service.FileMetadataService;
import pl.applicationserver.scorefilesmanager.service.FileService;
import pl.applicationserver.scorefilesmanager.service.StorageService;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    //TODO logger
    private StorageService storageService;
    private FileMetadataService fileMetadataService;
    private AbstractFileRepository fileRepository;
    private ArchivedFileMetadataService archivedFileMetadataService;

    @Autowired
    public FileServiceImpl(StorageService storageService, FileMetadataService fileMetadataService, AbstractFileRepository fileRepository, ArchivedFileMetadataService archivedFileMetadataService) {
        this.storageService = storageService;
        this.fileMetadataService = fileMetadataService;
        this.fileRepository = fileRepository;
        this.archivedFileMetadataService = archivedFileMetadataService;
    }

    @Override
    public boolean uploadFile(MultipartFile file, SimpleFileInfo fileInfo) {
        String generateFileName = generateFileName();
        String filePath = generateFilePath(fileInfo.getScoreFileType(), generateFileName);
        String pathToDownload = storageService.upload(file, filePath);
        String[] fileNameSplit = file.getOriginalFilename().split("\\.");
        String fileExtension = fileNameSplit[fileNameSplit.length - 1];
        return fileMetadataService.createFileMetadata(fileInfo, generateFileName, file.getSize(), pathToDownload, fileExtension);
    }

    @Override
    public Resource downloadFile(String fileName) {
        AbstractFileMetadata file = fileRepository.getByFileName(fileName);
        try {
            byte[] content = storageService.download(file.getUrl());
            return new ByteArrayResource(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DownloadedFile createDownloadedFile(String fileName) {
        String fileInBase64= downloadFileBase64(fileName);
        AbstractFileMetadata fileMetadata = fileMetadataService.get(fileName);
        if(fileInBase64 != null && fileMetadata != null) {
            return new DownloadedFile(fileInBase64, fileMetadata);
        }
        return null;
    }

    private String downloadFileBase64(String fileName) {
        AbstractFileMetadata file = fileRepository.getByFileName(fileName);
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
            AbstractFileMetadata file = fileRepository.getByFileName(fileName);
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
    public Boolean removeFilePermanently(Long id) {
        AbstractFileMetadata fileMetadata = fileRepository.getOne(id);
        byte[] content = storageService.download(fileMetadata.getUrl());
        boolean removedFromStorage = storageService.removePermanently(fileMetadata);
        if(removedFromStorage) {
            boolean stored = archivedFileMetadataService.store(fileMetadata, content);
            if (stored) {
                fileRepository.deleteById(id);
                return true;
            }
            return null;
        }
        return false;
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
