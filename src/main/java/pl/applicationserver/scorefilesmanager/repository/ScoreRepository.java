package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.applicationserver.scorefilesmanager.domain.*;

import java.util.List;
import java.util.Set;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> getAllByScoreTypeId(Long id);
    List<Score> getAllByScoreTypeAndScoreTitle(ScoreType scoreType, ScoreTitle scoreTitle);
    List<Score> getAllByInstrumentAndScoreTitle(Instrument instrument, ScoreTitle scoreTitle);
    List<Score> getAllByInstrumentAndScoreType(Instrument instrument, ScoreType scoreType);
    List<Score> getScoresByPdfFilesIsContaining(Set<SAFileMetadata> pdfSet);
}
