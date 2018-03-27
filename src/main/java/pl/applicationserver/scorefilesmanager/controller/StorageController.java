package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;
import pl.applicationserver.scorefilesmanager.repository.AbstractFileRepository;
import pl.applicationserver.scorefilesmanager.service.FileService;

import java.util.List;

@RestController
@RequestMapping(value = "/storage")
public class StorageController {
    private AbstractFileRepository fileRepository;
    private FileService fileService;

    @Autowired
    public StorageController(AbstractFileRepository fileRepository, FileService fileService) {
        this.fileRepository = fileRepository;
        this.fileService = fileService;
    }

    @GetMapping("/fileType/")
    public ResponseEntity<List<AbstractFileMetadata>>getFilesByFileType(@RequestParam ScoreFileType fileType){
        List<AbstractFileMetadata> files = fileRepository.getAllByScoreFileType(fileType);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }


    @GetMapping("/scoreType/")
    public ResponseEntity<List<AbstractFileMetadata>>getFilesByScoreType(@RequestParam ScoreType scoreType){
        List<AbstractFileMetadata> files = fileRepository.getAllByScoreType(scoreType);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/fileList")
    public ResponseEntity<List<String>> listFiles(@RequestParam("fileType") ScoreFileType fileType){
        List<String> files = fileService.getStorageFileListByFileType(fileType);
        return new ResponseEntity<>(files,HttpStatus.OK);
    }


    @GetMapping("/deleted")
    public ResponseEntity<List<AbstractFileMetadata>>getDeleted(){
        List<AbstractFileMetadata> files = fileRepository.getAllByDeletedNot(false);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }
}
