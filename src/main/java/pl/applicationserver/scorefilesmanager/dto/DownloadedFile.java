package pl.applicationserver.scorefilesmanager.dto;

import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadata;

public class DownloadedFile {

    private String content;
    private AbstractFileMetadata fileMetadata;

    public DownloadedFile(String content, AbstractFileMetadata fileMetadata) {
        this.content = content;
        this.fileMetadata = fileMetadata;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AbstractFileMetadata getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(AbstractFileMetadata fileMetadata) {
        this.fileMetadata = fileMetadata;
    }
}
