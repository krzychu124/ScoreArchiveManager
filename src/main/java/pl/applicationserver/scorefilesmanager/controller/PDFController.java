package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.PdfFileMetadata;
import pl.applicationserver.scorefilesmanager.dto.SimpleFileInfo;
import pl.applicationserver.scorefilesmanager.repository.AbstractFileRepository;
import pl.applicationserver.scorefilesmanager.service.FileService;


@RestController
@RequestMapping(value = "/pdf")
public class PDFController {
    private FileService fileService;
    private AbstractFileRepository pdfRepository;

    @Autowired
    public PDFController(FileService fileService, AbstractFileRepository pdfRepository) {
        this.fileService = fileService;
        this.pdfRepository = pdfRepository;
    }

    @PostMapping
    public ResponseEntity<AbstractFileMetadata> upload(@RequestParam("file") MultipartFile multipartFile, @RequestPart SimpleFileInfo fileInfo) {
        if (fileInfo != null && fileInfo.getTitleId() != null & fileInfo.getScoreFileType() != null) {
            AbstractFileMetadata uploadedFileMetadata = fileService.uploadFile(multipartFile, fileInfo);
            if (uploadedFileMetadata != null) {
                return new ResponseEntity<>(uploadedFileMetadata, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<Resource> download(@RequestParam("name") String fileName) {
        if (fileName != null) {
            PdfFileMetadata pdfFile = (PdfFileMetadata) pdfRepository.getByFileName(fileName);
            if (pdfFile != null) {
                Resource resource = fileService.downloadFile(fileName);
                if (resource != null) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename: " + AbstractFileMetadata.generateFileName(pdfFile));
                    return ResponseEntity.ok()
                            .headers(headers)
                            .contentLength(pdfFile.getFileSize())
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
