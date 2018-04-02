package pl.applicationserver.scorefilesmanager.dto;

import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;

public class DownloadedFile {

    private String content;
    private SAFileMetadata fileMetadata;

    public DownloadedFile(String content, SAFileMetadata fileMetadata) {
        this.content = content;
        this.fileMetadata = fileMetadata;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SAFileMetadata getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(SAFileMetadata fileMetadata) {
        this.fileMetadata = fileMetadata;
    }
}
