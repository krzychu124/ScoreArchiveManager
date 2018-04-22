package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.domain.Comment;
import pl.applicationserver.scorefilesmanager.domain.Job;
import pl.applicationserver.scorefilesmanager.domain.JobStatus;
import pl.applicationserver.scorefilesmanager.domain.JobType;
import pl.applicationserver.scorefilesmanager.dto.ExtendedJobDTO;
import pl.applicationserver.scorefilesmanager.dto.SimpleJobDTO;

import java.util.Set;

public interface JobService {

    Job createJob(Job job);
    ExtendedJobDTO getFullJobById(Long jobId);
    SimpleJobDTO getSimpleJobById(Long jobId);
    boolean updateJob(Long jobId, Job updatedJob);
    boolean addComment(Long jobId, Comment comment);
    Set<Comment> getJobComments(Long jobId);
    Set<JobType> getJobTypes();
    Set<JobStatus> getJobStatuses();

    Set<SimpleJobDTO> getSimpleJobsByStatus(Long statusId);
}
