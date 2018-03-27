package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;

public interface ScoreTypeRepository extends JpaRepository<ScoreType, Long> {
}
