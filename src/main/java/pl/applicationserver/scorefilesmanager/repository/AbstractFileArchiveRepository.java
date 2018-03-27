package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.applicationserver.scorefilesmanager.domain.AbstractFileMetadataArchive;

public interface AbstractFileArchiveRepository extends JpaRepository<AbstractFileMetadataArchive, Long> {

    AbstractFileMetadataArchive getByFileName(String fileName);
//    List<AbstractFileMetadata> getAbstractFilesByDeletedFalse();
//    AbstractFileMetadata getByFileName(String name);
//
//    default List<AbstractFileMetadata> getAllByDeletedNot() {
//        return getAllByDeletedNot(true);
//    }
//
//    List<AbstractFileMetadata> getAllByDeletedNot(boolean deleted);
//    List<AbstractFileMetadata> getAllByScoreFileType(ScoreFileType fileType);
//    List<AbstractFileMetadata> getAllByScoreType(ScoreType scoreType);
}
