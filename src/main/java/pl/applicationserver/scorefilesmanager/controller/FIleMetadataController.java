package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.service.FileMetadataService;

import java.util.List;

@RestController
@RequestMapping("/fileMetadata")
public class FIleMetadataController {
    private FileMetadataService fileMetadataService;


    public FIleMetadataController(FileMetadataService fileMetadataService) {
        this.fileMetadataService = fileMetadataService;
    }

    @GetMapping
    public ResponseEntity<List<AbstractFileMetadata>> getMetadataListByType(@RequestParam("fileType")ScoreFileType fileType) {
        return new ResponseEntity<>(fileMetadataService.getByType(fileType), HttpStatus.OK);
    }
}
