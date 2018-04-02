package pl.applicationserver.scorefilesmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sam_score")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @ManyToOne
    private ScoreTitle scoreTitle;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ScoreType scoreType;
    @NotNull
    @ManyToOne
    private User createdBy;
    @NotNull
    @ManyToOne
    private User lastModifiedBy;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Instrument instrument;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SAFileMetadata> pdfFiles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SAFileMetadata> museScoreFiles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SAFileMetadata> imageFiles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SAFileMetadata> otherFiles = new HashSet<>();
    @NotNull
    private Timestamp lastModificationTime;

    public Score() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScoreTitle getScoreTitle() {
        return scoreTitle;
    }

    public void setScoreTitle(ScoreTitle scoreTitle) {
        this.scoreTitle = scoreTitle;
    }

    public Timestamp getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(Timestamp lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public ScoreType getScoreType() {
        return scoreType;
    }

    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModificationTime = Timestamp.from(Instant.now());
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Set<SAFileMetadata> getOtherFiles() {
        return otherFiles;
    }

    public void setOtherFiles(Set<SAFileMetadata> otherFiles) {
        this.otherFiles = otherFiles;
    }

    public Set<SAFileMetadata> getPdfFiles() {
        return pdfFiles;
    }

    public void setPdfFiles(Set<SAFileMetadata> pdfFiles) {
        this.pdfFiles = pdfFiles;
    }

    public Set<SAFileMetadata> getMuseScoreFiles() {
        return museScoreFiles;
    }

    public void setMuseScoreFiles(Set<SAFileMetadata> museScoreFiles) {
        this.museScoreFiles = museScoreFiles;
    }

    public Set<SAFileMetadata> getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(Set<SAFileMetadata> imageFiles) {
        this.imageFiles = imageFiles;
    }
}
