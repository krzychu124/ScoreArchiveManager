package pl.applicationserver.scorefilesmanager.dto;

import pl.applicationserver.scorefilesmanager.domain.JobStatus;
import pl.applicationserver.scorefilesmanager.domain.JobType;

import java.sql.Timestamp;
import java.util.Set;

public interface ExtendedJobDTO {
    Long getId();
    String getName();
    JobType getJobType();
    JobStatus getJobStatus();
    Set<SimleSAFileMetadata> getAttachedFiles();
    String getDescription();
    Set<UserInfoDTO> getEditors();
    String getCreator();
    String getLastModifiedBy();
    Timestamp getCreated();
    Timestamp getModified();
    boolean getDeleted();
}
