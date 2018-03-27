package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.domain.ScoreTitle;

import java.util.List;
import java.util.Set;

public interface ScoreTitleService {

    List<ScoreTitle> getAll();

    Set<ScoreTitle> getAllByScoreType(Long scoreType);

    ScoreTitle getTitle(Long id);

    ScoreTitle createTitle(ScoreTitle scoreTitle);

    boolean removeTitle(Long scoreTitleId);

    ScoreTitle updateTitle(Long id, ScoreTitle scoreTitle);

    boolean exists(Long id);
}
