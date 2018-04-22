package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.applicationserver.scorefilesmanager.domain.Comment;
import pl.applicationserver.scorefilesmanager.domain.Job;
import pl.applicationserver.scorefilesmanager.domain.JobStatus;
import pl.applicationserver.scorefilesmanager.domain.JobType;
import pl.applicationserver.scorefilesmanager.dto.ExtendedJobDTO;
import pl.applicationserver.scorefilesmanager.dto.SimpleJobDTO;

import java.util.Set;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsByName(String name);

    @Query("select j from Job j where j.jobStatus.id =:statusId")
    Set<SimpleJobDTO> getJobsOfStatus(@Param("statusId")Long statusId);

    @Query("select j.comments from Job j where j.id =:jobId")
    Set<Comment> getCommentsOfJobId(@Param("jobId") Long jobId);

    @Query("select j from Job j where j.id =:jobId")
    SimpleJobDTO getSimpleJobById(@Param("jobId")Long jobId);

    @Query("select j from Job j where j.id =:jobId")
    ExtendedJobDTO getExtendedJobById(@Param("jobId")Long jobId);

    @Query("select j from JobType j")
    Set<JobType> getJobTypes();

    @Query("select s from JobStatus s")
    Set<JobStatus> getJobStatuses();
    @Query("select s from JobStatus s where s.name =:name")
    JobStatus getJobStatusOfName(@Param("name")String name);
}
