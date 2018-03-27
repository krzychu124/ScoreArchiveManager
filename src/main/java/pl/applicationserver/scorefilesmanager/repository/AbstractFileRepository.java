package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;

import java.util.List;

public interface AbstractFileRepository extends JpaRepository<AbstractFileMetadata, Long> {


    List<AbstractFileMetadata> getAbstractFilesByDeletedFalse();
    AbstractFileMetadata getByFileName(String name);

    default List<AbstractFileMetadata> getAllByDeletedNot() {
        return getAllByDeletedNot(true);
    }

    List<AbstractFileMetadata> getAllByDeletedNot(boolean deleted);
    List<AbstractFileMetadata> getAllByScoreFileType(ScoreFileType fileType);
    List<AbstractFileMetadata> getAllByScoreType(ScoreType scoreType);
}
