package pl.applicationserver.scorefilesmanager.service;

public interface ThumbnailCreatorService {
//    String createThumbnailBase64(SAFileMetadata fileMetadata);
    String createThumbnailBase64(byte[] pdfFileContent);
}
