package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.domain.ScoreBook;

import java.util.Set;

public interface ScoreBookService {

    Set<ScoreBook> getAllByScoreTypeId(Long scoreTypeId);
    ScoreBook getScoreBook(Long titleId, Long instrumentId);
    ScoreBook createScoreBook(ScoreBook scoreBook);
    boolean updateScoreBook(Long id, ScoreBook scoreBook);
    boolean removeScoreBook(Long scoreBookId);
    boolean addScoreToBook( Long scoreBookId, Long scoreId);
    boolean removeScoreFromBook(Long scoreBookId, Long scoreId);
    boolean exists(Long id);
}
