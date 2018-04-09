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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScoreServiceImpl implements ScoreService {
    private ScoreRepository scoreRepository;
    private UserRepository userRepository;
    private ScoreTitleRepository scoreTitleRepository;
    private InstrumentRepository instrumentRepository;
    private IAuthenticationFacade authenticationFacade;
    private ScoreTypeRepository scoreTypeRepository;
    private SAFileMetadataRepository saFileMetadataRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository, UserRepository userRepository, ScoreTitleRepository scoreTitleRepository, InstrumentRepository instrumentRepository, IAuthenticationFacade authenticationFacade, ScoreTypeRepository scoreTypeRepository, SAFileMetadataRepository saFileMetadataRepository) {
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
        this.scoreTitleRepository = scoreTitleRepository;
        this.instrumentRepository = instrumentRepository;
        this.authenticationFacade = authenticationFacade;
        this.scoreTypeRepository = scoreTypeRepository;
        this.saFileMetadataRepository = saFileMetadataRepository;
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
    @Transactional
    public Score createScore(Score score) {
        User authorizedUser = (User) authenticationFacade.getAuthentication().getPrincipal();
        User user = userRepository.findOneByUserName(authorizedUser.getUsername());
        if (user != null) {
            user = userRepository.getOne(user.getId());
            score.setInstrument(instrumentRepository.getOne(score.getInstrument().getId()));
            score.setScoreType(scoreTypeRepository.getOne(score.getScoreType().getId()));
            score.setScoreTitle(scoreTitleRepository.getOne(score.getScoreTitle().getId()));
            score.setPdfFiles(new HashSet<>(saFileMetadataRepository.findAllById(score.getPdfFiles().stream().map(SAFileMetadata::getId).collect(Collectors.toSet()))));
            score.setImageFiles(new HashSet<>(saFileMetadataRepository.findAllById(score.getImageFiles().stream().map(SAFileMetadata::getId).collect(Collectors.toSet()))));
            score.setMuseScoreFiles(new HashSet<>(saFileMetadataRepository.findAllById(score.getMuseScoreFiles().stream().map(SAFileMetadata::getId).collect(Collectors.toSet()))));
            score.setOtherFiles(new HashSet<>(saFileMetadataRepository.findAllById(score.getOtherFiles().stream().map(SAFileMetadata::getId).collect(Collectors.toSet()))));
            score.setCreatedBy(user);
            score.setLastModifiedBy(user);
            score.setLastModificationTime(Timestamp.from(Instant.now()));
            Score saved = scoreRepository.saveAndFlush(score);
            saved.getPdfFiles().forEach(file -> file.setScoreId(saved.getId()));
            saFileMetadataRepository.saveAll(saved.getPdfFiles());
            saved.getMuseScoreFiles().forEach(file -> file.setScoreId(saved.getId()));
            saFileMetadataRepository.saveAll(saved.getMuseScoreFiles());
            saved.getImageFiles().forEach(file -> file.setScoreId(saved.getId()));
            saFileMetadataRepository.saveAll(saved.getImageFiles());
            saved.getOtherFiles().forEach(file -> file.setScoreId(saved.getId()));
            saFileMetadataRepository.saveAll(saved.getOtherFiles());
            return scoreRepository.saveAndFlush(saved);
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
            updated.getOtherFiles().addAll(score.getOtherFiles());
            updated.setScoreTitle(score.getScoreTitle());
            return scoreRepository.save(updated);
        }
        return null;
    }

    @Override
    public void removePdfFromScore(Long id) {
        SAFileMetadata file = saFileMetadataRepository.getOne(id);
        Set<SAFileMetadata> set = new HashSet<>();
        set.add(file);
        List<Score> scores = scoreRepository.getScoresByPdfFilesIsContaining(set);
        scores.forEach(score -> {
            score.getPdfFiles().remove(file);
            scoreRepository.save(score);
        });
    }
}
