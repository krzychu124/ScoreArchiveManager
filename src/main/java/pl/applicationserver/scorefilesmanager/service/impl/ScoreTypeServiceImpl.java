package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;
import pl.applicationserver.scorefilesmanager.repository.ScoreTypeRepository;
import pl.applicationserver.scorefilesmanager.service.ScoreTypeService;

@Service
public class ScoreTypeServiceImpl implements ScoreTypeService {
    private ScoreTypeRepository scoreTypeRepository;

    @Autowired
    public ScoreTypeServiceImpl(ScoreTypeRepository scoreTypeRepository) {
        this.scoreTypeRepository = scoreTypeRepository;
    }

    @Override
    public ScoreType getScoreType(Long id) {
        return scoreTypeRepository.getOne(id);
    }

    @Override
    public boolean exists(Long id) {
        return scoreTypeRepository.existsById(id);
    }
}
