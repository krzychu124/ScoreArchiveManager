package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.applicationserver.scorefilesmanager.domain.ScoreTitle;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;

import java.util.Set;

@Repository
public interface ScoreTitleRepository extends JpaRepository<ScoreTitle, Long> {
    ScoreTitle getByTitle(String title);
    ScoreTitle getByTitleAndNumberAndScoreType(String title, Integer number, ScoreType scoreType);
    Set<ScoreTitle> getAllByScoreType(ScoreType scoreType);
}
