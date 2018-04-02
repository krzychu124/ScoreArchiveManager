package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.applicationserver.scorefilesmanager.domain.ArchivedFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.dto.DownloadedFile;
import pl.applicationserver.scorefilesmanager.dto.SimpleFileInfo;
import pl.applicationserver.scorefilesmanager.scheduler.PdfThumbnailGenerator;
import pl.applicationserver.scorefilesmanager.service.ArchivedFileMetadataService;
import pl.applicationserver.scorefilesmanager.service.FileMetadataService;
import pl.applicationserver.scorefilesmanager.service.FileService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FilesController {
    private FileService fileService;
    private FileMetadataService fileMetadataService;
    private ArchivedFileMetadataService archivedFileMetadataService;
    private PdfThumbnailGenerator pdfThumbnailGenerator;

    @Autowired
    public FilesController(FileService fileService, FileMetadataService fileMetadataService, ArchivedFileMetadataService archivedFileMetadataService, PdfThumbnailGenerator pdfThumbnailGenerator) {
        this.fileService = fileService;
        this.fileMetadataService = fileMetadataService;
        this.archivedFileMetadataService = archivedFileMetadataService;
        this.pdfThumbnailGenerator = pdfThumbnailGenerator;
    }

    @PostMapping
    public ResponseEntity<SAFileMetadata> upload(@RequestParam("file") MultipartFile multipartFile, @RequestPart SimpleFileInfo fileInfo) {
        if (fileInfo != null && fileInfo.getTitleId() != null & fileInfo.getScoreFileType() != null) {
            SAFileMetadata uploadedFileMetadata = fileService.uploadFile(multipartFile, fileInfo);
            pdfThumbnailGenerator.addToGeneratorTask(uploadedFileMetadata);
            if (uploadedFileMetadata != null) {
                return new ResponseEntity<>(uploadedFileMetadata, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<Resource> downloadAsResource(@RequestParam("fileName") String fileName) {
        if (fileName != null) {
            Resource resource = fileService.downloadFile(fileName);
            SAFileMetadata fileMetadata = fileMetadataService.get(fileName);
            if (resource != null && fileMetadata != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,HttpHeaders.CONTENT_DISPOSITION);
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename: " + SAFileMetadata.generateFileName(fileMetadata));
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(fileMetadata.getFileSize())
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .body(resource);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/archived")
    public ResponseEntity<List<ArchivedFileMetadata>> getAllArchived() {
        return new ResponseEntity<>(archivedFileMetadataService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/archived/file")
    public ResponseEntity<ArchivedFileMetadata> getAllArchivedByName(@RequestParam("fileName") String fileName) {
        ArchivedFileMetadata archivedFile = archivedFileMetadataService.getByFileName(fileName);
        if(archivedFile != null) {
            return new ResponseEntity<>(archivedFile, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/base64")
    public ResponseEntity<DownloadedFile> downloadBase64(@RequestParam("fileName") String fileName) {
        if (fileName != null) {
            DownloadedFile downloadedFile = fileService.createDownloadedFile(fileName);
            if (downloadedFile != null) {
                return new ResponseEntity<>(downloadedFile, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{fileName}/thumb")
    public ResponseEntity<Void> generateThumbnail(@PathVariable("fileName") String fileName) {
        boolean generated = fileService.generateThumbnail(fileName);
        if(generated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Boolean deleted;
        try {
            deleted = fileService.removeFilePermanently(id);
        } catch (IOException e) {
            System.out.println(e.getMessage() + " " + e.getCause());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(deleted != null) {
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);// if error when trying to set as archive metadata
    }

    @DeleteMapping("/fileName/{fileName}")
    public ResponseEntity<Void> markAsDeleted(@PathVariable("fileName") String fileName) {
        boolean deleted = fileService.removeFile(fileName);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
