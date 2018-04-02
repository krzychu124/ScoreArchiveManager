package pl.applicationserver.scorefilesmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sam_archived_file_metadata")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ArchivedFileMetadata{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @NotNull
    private String fileName;
    @NotNull
    @ManyToOne
    private ScoreTitle scoreTitle;
    @ManyToOne
    private ScoreType scoreType;
    @NotNull
    @ManyToOne
    private Instrument instrument;
    private Long scoreId;
    @NotNull
    private String url;
    @NotNull
    private Long fileSize;
    @NotNull
    private String fileExtension;
    @NotNull
    @Enumerated
    private ScoreFileType scoreFileType;
    @NotNull
    private boolean deleted;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] content;

    public ArchivedFileMetadata(SAFileMetadata fileMetadata) {
        this.id = fileMetadata.getId();
        this.fileName = fileMetadata.getFileName();
        this.scoreTitle = fileMetadata.getScoreTitle();
        this.scoreType = fileMetadata.getScoreType();
        this.instrument = fileMetadata.getInstrument();
        this.scoreId = fileMetadata.getScoreId();
        this.url = fileMetadata.getUrl();
        this.fileSize = fileMetadata.getFileSize();
        this.fileExtension = fileMetadata.getFileExtension();
        this.scoreFileType = fileMetadata.getScoreFileType();
        this.deleted = fileMetadata.isDeleted();
    }

    public ArchivedFileMetadata() {
    }

    public ScoreTitle getScoreName() {
        return scoreTitle;
    }

    public byte[] getContent() {
        return content;
    }

    public void setScoreName(ScoreTitle scoreTitle) {
        this.scoreTitle = scoreTitle;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public ScoreFileType getScoreFileType() {
        return scoreFileType;
    }

    public void setScoreFileType(ScoreFileType scoreFileType) {
        this.scoreFileType = scoreFileType;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ScoreType getScoreType() {
        return scoreType;
    }

    public ScoreTitle getScoreTitle() {
        return scoreTitle;
    }

    public void setScoreTitle(ScoreTitle scoreTitle) {
        this.scoreTitle = scoreTitle;
    }

    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }

    public String getUri() {
        return url;
    }

    public void setUri(String url) {
        this.url = url;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public static String generateFileName(ArchivedFileMetadata metadata) {
        return metadata.getScoreTitle().getTitle() + metadata.getInstrument().getName() + "." + metadata.getFileExtension();
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
