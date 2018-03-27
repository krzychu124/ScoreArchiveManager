package pl.applicationserver.scorefilesmanager.dto;

import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;

public class SimpleFileInfo {

    private Long titleId;
    private Long scoreTypeId;
    private Long instrumentId;
    private ScoreFileType scoreFileType;

    public SimpleFileInfo() {
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public Long getScoreTypeId() {
        return scoreTypeId;
    }

    public void setScoreTypeId(Long scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public ScoreFileType getScoreFileType() {
        return scoreFileType;
    }

    public void setScoreFileType(ScoreFileType scoreFileType) {
        this.scoreFileType = scoreFileType;
    }
}
