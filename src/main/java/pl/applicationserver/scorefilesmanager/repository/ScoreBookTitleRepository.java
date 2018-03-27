package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.applicationserver.scorefilesmanager.domain.ScoreBookTitle;

@Repository
public interface ScoreBookTitleRepository extends JpaRepository<ScoreBookTitle, Long> {
    ScoreBookTitle getByName(String name);
}
