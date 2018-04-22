package pl.applicationserver.scorefilesmanager.dto;

import pl.applicationserver.scorefilesmanager.domain.JobStatus;
import pl.applicationserver.scorefilesmanager.domain.JobType;

import java.sql.Timestamp;
import java.util.Set;

public interface SimpleJobDTO {
    Long getId();
    String getName();
    JobType getJobType();
    JobStatus getJobStatus();
    Set<SimleSAFileMetadata> getAttachedFiles();
    String getDescription();
    Set<SimpleUserDTO> getEditors();
    Timestamp getCreated();
    Timestamp getModified();
}
