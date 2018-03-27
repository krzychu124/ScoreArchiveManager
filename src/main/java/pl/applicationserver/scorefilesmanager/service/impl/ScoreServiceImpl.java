package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.component.IAuthenticationFacade;
import pl.applicationserver.scorefilesmanager.domain.*;
import pl.applicationserver.scorefilesmanager.repository.*;
import pl.applicationserver.scorefilesmanager.service.ScoreService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class ScoreServiceImpl implements ScoreService {
    private ScoreRepository scoreRepository;
    private UserRepository userRepository;
    private ScoreTitleRepository scoreTitleRepository;
    private InstrumentRepository instrumentRepository;
    private IAuthenticationFacade authenticationFacade;
    private ScoreTypeRepository scoreTypeRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository, UserRepository userRepository, ScoreTitleRepository scoreTitleRepository, InstrumentRepository instrumentRepository, IAuthenticationFacade authenticationFacade, ScoreTypeRepository scoreTypeRepository) {
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
        this.scoreTitleRepository = scoreTitleRepository;
        this.instrumentRepository = instrumentRepository;
        this.authenticationFacade = authenticationFacade;
        this.scoreTypeRepository = scoreTypeRepository;
    }

    @Override
    public List<Score> getScoresByTypeAndTitle(ScoreType scoreType, ScoreTitle title) {
        return scoreRepository.getAllByScoreTypeAndScoreTitle(scoreType, title);
    }

    @Override
    public List<ScoreType> getScoreTypes() {
        return scoreTypeRepository.findAll();
    }

    @Override
    public List<Score> getScoresByScoreType(Long scoreTypeId) {
        return scoreRepository.getAllByScoreTypeId(scoreTypeId);
    }

    @Override
    public List<Score> getScoresByInstrumentAndTitle(Long instrumentId, Long scoreTitleId) {
        ScoreTitle title = scoreTitleRepository.getOne(scoreTitleId);
        Instrument instrument = instrumentRepository.getOne(instrumentId);
        return scoreRepository.getAllByInstrumentAndScoreTitle(instrument, title);
    }

    @Override
    public List<Score> getScoresByInstrumentAndTScoreType(Long instrumentId, Long scoreTypeId) {
        ScoreType scoreType = scoreTypeRepository.getOne(scoreTypeId);
        Instrument instrument = instrumentRepository.getOne(instrumentId);
        return scoreRepository.getAllByInstrumentAndScoreType(instrument, scoreType);
    }

    @Override
    public Score getScore(Long scoreId) {
        return scoreRepository.getOne(scoreId);
    }

    @Override
    public Score createScore(Score score) {
        String userName = (String) authenticationFacade.getAuthentication().getPrincipal();
        User user = userRepository.getUserByName(userName);
        if (user != null) {
            user = userRepository.getOne(user.getId());
            score.setCreatedBy(user);
            score.setLastModifiedBy(user);
            score.setLastModificationTime(Timestamp.from(Instant.now()));
            return scoreRepository.save(score);
        }
        return null;
    }

    @Override
    public boolean removeScore(Long scoreId) {
        if (scoreRepository.existsById(scoreId)) {
            scoreRepository.deleteById(scoreId);
            return true;
        }
        return false;
    }

    @Override
    public Score updateScore(Long scoreId, Score score) {
        Score updated = null;
        try {
            updated = scoreRepository.getOne(scoreId);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        if (updated != null) {
            updated.setLastModifiedBy(user);
            updated.setScoreType(score.getScoreType());
            updated.setInstrument(score.getInstrument());
            updated.getImageFiles().addAll(score.getImageFiles());
            updated.getMuseScoreFiles().addAll(score.getMuseScoreFiles());
            updated.getPdfFiles().addAll(score.getPdfFiles());
            updated.setScoreTitle(score.getScoreTitle());
            return scoreRepository.save(updated);
        }
        return null;
    }
}
