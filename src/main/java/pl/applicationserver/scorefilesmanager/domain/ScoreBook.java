package pl.applicationserver.scorefilesmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ScoreBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @ManyToOne
    private ScoreType scoreType;
    @ManyToOne
    private ScoreBookTitle scoreBookTitle;
    @ManyToOne
    private Instrument instrument;
    @OneToMany
    private Set<Score> scoreList = new HashSet<>();

    public ScoreBook() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScoreBookTitle getScoreBookTitle() {
        return scoreBookTitle;
    }

    public void setScoreBookTitle(ScoreBookTitle scoreBookTitle) {
        this.scoreBookTitle = scoreBookTitle;
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

    public Set<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(Set<Score> scoreList) {
        this.scoreList = scoreList;
    }
}
