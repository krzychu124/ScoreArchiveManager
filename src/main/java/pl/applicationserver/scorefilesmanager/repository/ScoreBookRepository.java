package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.applicationserver.scorefilesmanager.domain.ScoreBook;
import pl.applicationserver.scorefilesmanager.domain.ScoreBookTitle;

import java.util.Set;

@Repository
public interface ScoreBookRepository extends JpaRepository<ScoreBook, Long>{
    ScoreBook getByScoreBookTitleAndInstrumentId(ScoreBookTitle scoreBookTitle, Long instrumentId);
    Set<ScoreBook> getByScoreTypeId(Long scoreTypeId);
}
