package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.service.FileMetadataService;

import java.util.List;

@RestController
@RequestMapping("/api/fileMetadata")
public class FIleMetadataController {
    private FileMetadataService fileMetadataService;


    public FIleMetadataController(FileMetadataService fileMetadataService) {
        this.fileMetadataService = fileMetadataService;
    }

    @GetMapping
    public ResponseEntity<List<SAFileMetadata>> getMetadataListByType(@RequestParam("fileType") ScoreFileType fileType) {
        return new ResponseEntity<>(fileMetadataService.getByType(fileType), HttpStatus.OK);
    }

    @GetMapping("/orchestra")
    public ResponseEntity<List<SAFileMetadata>> getMetadataListOrchestraByType(@RequestParam("fileType") ScoreFileType fileType) {
        return new ResponseEntity<>(fileMetadataService.getByTypeOrchestra(fileType), HttpStatus.OK);
    }
    @GetMapping("/band")
    public ResponseEntity<List<SAFileMetadata>> getMetadataListJBBandByType(@RequestParam("fileType") ScoreFileType fileType) {
        return new ResponseEntity<>(fileMetadataService.getByTypeJBBand(fileType), HttpStatus.OK);
    }

    @GetMapping("/instrument/{id}")
    public ResponseEntity<List<SAFileMetadata>> getAllByInstrument(@PathVariable("id") Long instrumentId) {
        List<SAFileMetadata> files = fileMetadataService.getAllByInstrument(instrumentId);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }
    @GetMapping("/instrument/orchestra/{id}")
    public ResponseEntity<List<SAFileMetadata>> getAllOrchestraByInstrument(@PathVariable("id") Long instrumentId) {
        List<SAFileMetadata> files = fileMetadataService.getAllByInstrument(instrumentId);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }
    @GetMapping("/instrument/band/{id}")
    public ResponseEntity<List<SAFileMetadata>> getAllJBBandByInstrument(@PathVariable("id") Long instrumentId) {
        List<SAFileMetadata> files = fileMetadataService.getAllByInstrument(instrumentId);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }
}
