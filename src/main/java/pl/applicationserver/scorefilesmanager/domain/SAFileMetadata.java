package pl.applicationserver.scorefilesmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "sam_sa_file_metadata")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SAFileMetadata {

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
    private String thumbnail;
    private Timestamp created;
    @NotNull
    private boolean deleted = false;

    public SAFileMetadata(@NotNull String fileName, ScoreTitle scoreTitle, @NotNull ScoreType scoreType, Instrument instrument, @NotNull String url, @NotNull Long fileSize, String fileExtension, ScoreFileType scoreFileType) {
        this.fileName = fileName;
        this.scoreTitle = scoreTitle;
        this.scoreType = scoreType;
        this.instrument = instrument;
        this.url = url;
        this.fileSize = fileSize;
        this.fileExtension = fileExtension;
        this.scoreFileType = scoreFileType;
    }

    protected SAFileMetadata() {
    }

    @PrePersist
    protected void onCreate() {
        created = Timestamp.from(Instant.now());
    }

    public Long getId() {
        return id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ScoreTitle getScoreTitle() {
        return scoreTitle;
    }

    public void setScoreTitle(ScoreTitle scoreTitle) {
        this.scoreTitle = scoreTitle;
    }

    public ScoreType getScoreType() {
        return scoreType;
    }

    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
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

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public ScoreFileType getScoreFileType() {
        return scoreFileType;
    }

    public void setScoreFileType(ScoreFileType scoreFileType) {
        this.scoreFileType = scoreFileType;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public static String generateFileName(SAFileMetadata metadata) {
        return metadata.getScoreTitle().getTitle() + metadata.getInstrument().getName() + "." + metadata.getFileExtension();
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
