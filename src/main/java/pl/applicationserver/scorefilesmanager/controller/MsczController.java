package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.dto.SimpleFileInfo;
import pl.applicationserver.scorefilesmanager.repository.SAFileMetadataRepository;
import pl.applicationserver.scorefilesmanager.service.FileService;

@RestController
@RequestMapping(value = "/mscz")
public class MsczController {


    private FileService fileService;
    private SAFileMetadataRepository msczRepository;

    @Autowired
    public MsczController(FileService fileService, SAFileMetadataRepository msczRepository) {
        this.fileService = fileService;
        this.msczRepository = msczRepository;
    }

    @PostMapping
    public ResponseEntity<SAFileMetadata> upload(@RequestParam("file") MultipartFile multipartFile, @RequestPart SimpleFileInfo fileInfo) {
        if (fileInfo != null && fileInfo.getTitleId() != null & fileInfo.getScoreFileType() != null && fileInfo.getInstrumentId() != null) {
            SAFileMetadata uploadedFileMetadata = fileService.uploadFile(multipartFile, fileInfo);
            if (uploadedFileMetadata != null) {
                return new ResponseEntity<>(uploadedFileMetadata, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<Resource> download(@RequestBody String fileName) {
        if (fileName != null) {
            SAFileMetadata msczFile = msczRepository.getByFileName(fileName);
            if (msczFile != null) {
                Resource resource = fileService.downloadFile(fileName);
                if (resource != null) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename: " + msczFile.getScoreTitle().getTitle());
                    return ResponseEntity.ok()
                            .headers(headers)
                            .contentLength(msczFile.getFileSize())
                            .contentType(MediaType.parseMediaType("application/octet-stream"))
                            .body(resource);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("fileName") String fileName) {
        boolean deleted = fileService.removeFile(fileName);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
