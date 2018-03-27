package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.Score;
import pl.applicationserver.scorefilesmanager.domain.ScoreBook;
import pl.applicationserver.scorefilesmanager.domain.ScoreBookTitle;
import pl.applicationserver.scorefilesmanager.repository.ScoreBookRepository;
import pl.applicationserver.scorefilesmanager.repository.ScoreBookTitleRepository;
import pl.applicationserver.scorefilesmanager.repository.ScoreRepository;
import pl.applicationserver.scorefilesmanager.service.ScoreBookService;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
public class ScoreBookServiceImpl implements ScoreBookService {
    private ScoreBookRepository scoreBookRepository;
    private ScoreBookTitleRepository scoreBookTitleRepository;
    private ScoreRepository scoreRepository;
    @Autowired
    public ScoreBookServiceImpl(ScoreBookRepository scoreBookRepository, ScoreBookTitleRepository scoreBookTitleRepository, ScoreRepository scoreRepository) {
        this.scoreBookRepository = scoreBookRepository;
        this.scoreBookTitleRepository = scoreBookTitleRepository;
        this.scoreRepository = scoreRepository;
    }

    @Override
    public Set<ScoreBook> getAllByScoreTypeId(Long scoreType) {

        return scoreBookRepository.getByScoreTypeId(scoreType);
    }

    @Override
    public ScoreBook getScoreBook(Long titleId, Long instrumentId) {
        ScoreBookTitle scoreBookTitle = scoreBookTitleRepository.getOne(titleId);
        return scoreBookRepository.getByScoreBookTitleAndInstrumentId(scoreBookTitle, instrumentId);
    }

    @Override
    public ScoreBook createScoreBook(ScoreBook scoreBook) {
        return scoreBookRepository.save(scoreBook);
    }

    @Override
    public boolean updateScoreBook(Long id, ScoreBook scoreBook) {

        ScoreBook updated;
        try {
            updated = scoreBookRepository.getOne(id);
            updated.setScoreBookTitle(scoreBook.getScoreBookTitle());
            updated.setInstrument(scoreBook.getInstrument());
            updated.setScoreType(scoreBook.getScoreType());
            updated.getScoreList().addAll(scoreBook.getScoreList());
            scoreBookRepository.save(updated);
            return true;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeScoreBook(Long scoreBookId) {
        if(exists(scoreBookId)) {
            scoreBookRepository.deleteById(scoreBookId);
            return true;
        }
        return false;
    }

    @Override
    public boolean addScoreToBook(Long scoreBookId, Long scoreId) {
        ScoreBook scoreBook = scoreBookRepository.getOne(scoreBookId);
        Score score;
        try {
            score = scoreRepository.getOne(scoreId);
            scoreBook.getScoreList().add(score);
            scoreBookRepository.save(scoreBook);
            return true;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeScoreFromBook(Long scoreBookId, Long scoreId) {
        ScoreBook scoreBook = scoreBookRepository.getOne(scoreBookId);
        Score score;
        try {
            score = scoreRepository.getOne(scoreId);
            scoreBook.getScoreList().remove(score);
            scoreBookRepository.save(scoreBook);
            return true;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean exists(Long id) {
        return scoreBookRepository.existsById(id);
    }
}
