package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.ScoreTitle;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;
import pl.applicationserver.scorefilesmanager.repository.ScoreTitleRepository;
import pl.applicationserver.scorefilesmanager.repository.ScoreTypeRepository;
import pl.applicationserver.scorefilesmanager.service.ScoreTitleService;

import java.util.List;
import java.util.Set;

@Service
public class ScoreTitleServiceImpl implements ScoreTitleService {

    private ScoreTitleRepository titleRepository;
    private ScoreTypeRepository scoreTypeRepository;

    @Autowired
    public ScoreTitleServiceImpl(ScoreTitleRepository titleRepository, ScoreTypeRepository scoreTypeRepository) {
        this.titleRepository = titleRepository;
        this.scoreTypeRepository = scoreTypeRepository;
    }

    @Override
    public List<ScoreTitle> getAll() {
        return titleRepository.findAll();
    }

    @Override
    public Set<ScoreTitle> getAllByScoreType(Long scoreTypeId) {
        ScoreType scoreType = scoreTypeRepository.getOne(scoreTypeId);
        return titleRepository.getAllByScoreType(scoreType);
    }

    @Override
    public ScoreTitle getTitle(Long id) {
        return titleRepository.getOne(id);
    }

    @Override
    public ScoreTitle createTitle(ScoreTitle scoreTitle) {
        ScoreTitle duplicate = titleRepository.getByTitleAndNumberAndScoreType(scoreTitle.getTitle(),scoreTitle.getNumber(),scoreTitle.getScoreType());
        if(duplicate == null) {
            ScoreTitle title = new ScoreTitle();
            title.setNumber(scoreTitle.getNumber());
            title.setTitle(scoreTitle.getTitle());
            title.setScoreType(scoreTitle.getScoreType());
            titleRepository.save(title);
            return title;
        }
        return duplicate;
    }

    @Override
    public boolean removeTitle(Long scoreTitleId) {
        if(exists(scoreTitleId)) {
            titleRepository.deleteById(scoreTitleId);
            return true;
        }
        return false;
    }

    @Override
    public ScoreTitle updateTitle(Long id, ScoreTitle scoreTitle) {
        ScoreTitle updated = getTitle(id);
        if(updated !=null) {
            updated.setTitle(scoreTitle.getTitle());
            updated.setNumber(scoreTitle.getNumber());
            updated.setScoreType(scoreTitle.getScoreType());
        }
        return updated;
    }

    @Override
    public boolean exists(Long id) {
        return titleRepository.existsById(id);
    }
}
