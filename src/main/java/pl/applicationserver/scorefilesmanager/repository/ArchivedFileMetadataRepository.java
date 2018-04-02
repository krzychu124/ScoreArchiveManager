package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.applicationserver.scorefilesmanager.domain.ArchivedFileMetadata;

public interface ArchivedFileMetadataRepository extends JpaRepository<ArchivedFileMetadata, Long> {

    ArchivedFileMetadata getByFileName(String fileName);
//    List<SAFileMetadata> getAllByDeletedFalse();
//    SAFileMetadata getByFileName(String name);
//
//    default List<SAFileMetadata> getAllByDeletedNot() {
//        return getAllByDeletedNot(true);
//    }
//
//    List<SAFileMetadata> getAllByDeletedNot(boolean deleted);
//    List<SAFileMetadata> getAllByScoreFileType(ScoreFileType fileType);
//    List<SAFileMetadata> getAllByScoreType(ScoreType scoreType);
}
