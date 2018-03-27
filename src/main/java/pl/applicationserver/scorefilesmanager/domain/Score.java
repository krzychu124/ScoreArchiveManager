package pl.applicationserver.scorefilesmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @ManyToOne
    private ScoreTitle scoreTitle;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private ScoreType scoreType;
    @NotNull
    @ManyToOne
    private User createdBy;
    @NotNull
    @ManyToOne
    private User lastModifiedBy;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Instrument instrument;
    @OneToMany
    private Set<PdfFileMetadata> pdfFiles = new HashSet<>();
    @OneToMany
    private Set<MuseScoreFileMetadata> museScoreFiles = new HashSet<>();
    @OneToMany
    private Set<ImageFileMetadata> imageFiles = new HashSet<>();
    @NotNull
    private Timestamp lastModificationTime;

    public Score() {
    }

    public Long getId() {
        return id;
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

    public Set<PdfFileMetadata> getPdfFiles() {
        return pdfFiles;
    }

    public Set<MuseScoreFileMetadata> getMuseScoreFiles() {
        return museScoreFiles;
    }

    public Set<ImageFileMetadata> getImageFiles() {
        return imageFiles;
    }

    public void setLastModificationTime(Timestamp lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }
}
