package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.domain.ScoreFileType;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;
import pl.applicationserver.scorefilesmanager.dto.SimleSAFileMetadata;

import java.util.List;

public interface SAFileMetadataRepository extends JpaRepository<SAFileMetadata, Long> {


    List<SAFileMetadata> getAllByDeletedFalse();

    SAFileMetadata getByFileName(String name);

    @Query("select sa from SAFileMetadata sa where sa.fileName =:name")
    SimleSAFileMetadata getSimpleByFileName(@Param("name") String name);

    @Query("select sa from SAFileMetadata sa where sa.deleted = false")
    List<SimleSAFileMetadata> getAllSimpleNotDeleted();


    default List<SAFileMetadata> getAllByDeletedNot() {
        return getAllByDeletedNot(true);
    }

    List<SAFileMetadata> getAllByDeletedNot(boolean deleted);

    List<SAFileMetadata> getAllByScoreFileType(ScoreFileType fileType);

    @Query("select sa from SAFileMetadata sa where sa.scoreFileType =:fileType")
    List<SimleSAFileMetadata> getAllSimpleByScoreFileType(@Param("fileType") ScoreFileType fileType);

    List<SAFileMetadata> getAllByScoreType(ScoreType scoreType);

    @Query("select sa from SAFileMetadata sa where sa.scoreType =:scoreType")
    List<SimleSAFileMetadata> getAllSimpleByScoreType(@Param("scoreType") ScoreType scoreType);

    List<SAFileMetadata> getAllByInstrumentId(Long instrumentId);

    @Query("select sa from SAFileMetadata sa where sa.instrument.id =:instrumentId")
    List<SimleSAFileMetadata> getAllSimpleByInstrumentId(@Param("instrumentId") Long instrumentId);

    List<SAFileMetadata> getAllByScoreFileTypeAndThumbnailIsNull(ScoreFileType scoreFileType);
}
