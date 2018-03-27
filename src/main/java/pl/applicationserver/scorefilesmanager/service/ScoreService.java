package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.domain.Score;
import pl.applicationserver.scorefilesmanager.domain.ScoreTitle;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;

import java.util.List;

public interface ScoreService {

    List<ScoreType> getScoreTypes();
    List<Score> getScoresByScoreType(Long scoreTypeId);
    List<Score> getScoresByTypeAndTitle(ScoreType scoreType, ScoreTitle title);
    List<Score> getScoresByInstrumentAndTitle(Long instrumentId, Long scoreTitleId);
    List<Score> getScoresByInstrumentAndTScoreType(Long instrumentId, Long scoreTypeId);
    Score getScore(Long scoreId);
    Score createScore(Score score);
    boolean removeScore(Long scoreId);
    Score updateScore(Long scoreId, Score score);
}
