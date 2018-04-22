package pl.applicationserver.scorefilesmanager.dto;

import pl.applicationserver.scorefilesmanager.domain.Instrument;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.domain.ScoreTitle;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;

public interface SimleSAFileMetadata {
    Long getId();
    String getFileName();
    ScoreTitle getScoreTitle();
    Instrument getInstrument();
    Long getScoreId();
    ScoreType getScoreType();
    ScoreFileType getScoreFileType();
}
