package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.ScoreBookTitle;
import pl.applicationserver.scorefilesmanager.repository.ScoreBookTitleRepository;
import pl.applicationserver.scorefilesmanager.service.ScoreBookTitleService;

import java.util.List;

@Service
public class ScoreBookTitleServiceImpl implements ScoreBookTitleService {
    private ScoreBookTitleRepository scoreBookTitleRepository;

    @Autowired
    public ScoreBookTitleServiceImpl(ScoreBookTitleRepository scoreBookTitleRepository) {
        this.scoreBookTitleRepository = scoreBookTitleRepository;
    }


    @Override
    public ScoreBookTitle getTitle(Long id) {
        return scoreBookTitleRepository.getOne(id);
    }

    @Override
    public ScoreBookTitle getTitle(String name) {
        return scoreBookTitleRepository.getByName(name);
    }

    @Override
    public List<ScoreBookTitle> getAll() {
        return scoreBookTitleRepository.findAll();
    }

    @Override
    public ScoreBookTitle createTitle(ScoreBookTitle title) {
        if(getTitle(title.getName()) == null){
            return scoreBookTitleRepository.save(title);
        }
        return null;
    }

    @Override
    public boolean removeTitle(Long titleId) {
        if (exists(titleId)) {
            scoreBookTitleRepository.deleteById(titleId);
            return true;
        }
        return false;
    }

    @Override
    public ScoreBookTitle updateTitle(Long id, ScoreBookTitle newTitle) {
        ScoreBookTitle updated = getTitle(id);
        if(updated != null) {
            if(!updated.getName().equals(newTitle.getName())){
                updated.setName(newTitle.getName());
                scoreBookTitleRepository.save(updated);
            }
            return updated;
        }
        return null;
    }

    private boolean exists(Long id){
        return scoreBookTitleRepository.existsById(id);
    }
}
