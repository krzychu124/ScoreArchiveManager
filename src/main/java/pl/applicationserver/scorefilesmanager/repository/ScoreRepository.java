package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.applicationserver.scorefilesmanager.domain.Instrument;
import pl.applicationserver.scorefilesmanager.domain.Score;
import pl.applicationserver.scorefilesmanager.domain.ScoreTitle;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> getAllByScoreTypeId(Long id);
    List<Score> getAllByScoreTypeAndScoreTitle(ScoreType scoreType, ScoreTitle scoreTitle);
    List<Score> getAllByInstrumentAndScoreTitle(Instrument instrument, ScoreTitle scoreTitle);
    List<Score> getAllByInstrumentAndScoreType(Instrument instrument, ScoreType scoreType);

}
