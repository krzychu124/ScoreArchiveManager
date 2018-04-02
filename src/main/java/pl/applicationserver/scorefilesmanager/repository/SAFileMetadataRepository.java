package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;

import java.util.List;

public interface SAFileMetadataRepository extends JpaRepository<SAFileMetadata, Long> {


    List<SAFileMetadata> getAllByDeletedFalse();
    SAFileMetadata getByFileName(String name);

    default List<SAFileMetadata> getAllByDeletedNot() {
        return getAllByDeletedNot(true);
    }

    List<SAFileMetadata> getAllByDeletedNot(boolean deleted);
    List<SAFileMetadata> getAllByScoreFileType(ScoreFileType fileType);
    List<SAFileMetadata> getAllByScoreType(ScoreType scoreType);
    List<SAFileMetadata> getAllByInstrumentId(Long instrumentId);
    List<SAFileMetadata> getAllByScoreFileTypeAndThumbnailIsNull(ScoreFileType scoreFileType);
}
