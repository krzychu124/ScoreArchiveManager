package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.domain.ScoreBookTitle;

import java.util.List;

public interface ScoreBookTitleService {

    ScoreBookTitle getTitle(Long id);
    ScoreBookTitle getTitle(String name);
    List<ScoreBookTitle> getAll();
    ScoreBookTitle createTitle(ScoreBookTitle title);
    boolean removeTitle(Long titleId);
    ScoreBookTitle updateTitle(Long id, ScoreBookTitle newTitle);
}
