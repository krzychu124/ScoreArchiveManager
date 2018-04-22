package pl.applicationserver.scorefilesmanager.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.component.IAuthenticationFacade;
import pl.applicationserver.scorefilesmanager.domain.*;
import pl.applicationserver.scorefilesmanager.dto.ExtendedJobDTO;
import pl.applicationserver.scorefilesmanager.dto.SimpleJobDTO;
import pl.applicationserver.scorefilesmanager.repository.CommentRepository;
import pl.applicationserver.scorefilesmanager.repository.JobRepository;
import pl.applicationserver.scorefilesmanager.repository.UserRepository;
import pl.applicationserver.scorefilesmanager.service.JobService;

import javax.transaction.Transactional;
import java.util.Set;

import static pl.applicationserver.scorefilesmanager.app.constants.AppConstants.TYPE_JOB;

@Service
@Transactional
public class JobServiceImpl implements JobService {
    Logger logger = LogManager.getLogger(JobServiceImpl.class);
    private JobRepository jobRepository;
    private IAuthenticationFacade authenticationFacade;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    public JobServiceImpl(JobRepository jobRepository, IAuthenticationFacade authenticationFacade, UserRepository userRepository, CommentRepository commentRepository) {
        this.jobRepository = jobRepository;
        this.authenticationFacade = authenticationFacade;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Job createJob(Job job) {
        job.setJobStatus(jobRepository.getJobStatusOfName("new"));
        job.getComments().forEach(comment -> {
            CommentSource source = new CommentSource();
            source.setSource(TYPE_JOB);
            comment.setCommentSource(source);
        });
        Job newJob = jobRepository.save(job);
        newJob.getComments().forEach(comment -> comment.getCommentSource().setIdInSource(newJob.getId()));
        return jobRepository.save(newJob);
    }

    @Override
    public ExtendedJobDTO getFullJobById(Long jobId) {
        return jobRepository.getExtendedJobById(jobId);
    }

    @Override
    public SimpleJobDTO getSimpleJobById(Long jobId) {
        return jobRepository.getSimpleJobById(jobId);
    }

    @Override
    public boolean updateJob(Long jobId, Job updatedJob) {
        Job job = jobRepository.existsById(jobId)? jobRepository.getOne(jobId): null;
        if(job != null) {
            User user = (User)authenticationFacade.getAuthentication().getPrincipal();
            user = userRepository.getOne(user.getId());
            job.getEditors().add(user);
            job.setJobType(jobRepository.getJobTypes().stream().filter(j->j.getId().equals(updatedJob.getJobType().getId())).findFirst().get());
            job.setName(updatedJob.getName());
            jobRepository.save(job);
            return true;
        }
        logger.warn("UpdateJob:: Job with id "+ jobId + " not found");
        return false;
    }

    @Override
    public boolean addComment(Long jobId, Comment comment) {
        Job job = jobRepository.existsById(jobId)? jobRepository.getOne(jobId): null;
        if(job != null) {
            CommentSource commentSource = new CommentSource();
            commentSource.setSource(TYPE_JOB);
            commentSource.setIdInSource(jobId);
            comment.setCommentSource(commentSource);
            Comment c = commentRepository.save(comment);
            job.getComments().add(c);
            jobRepository.save(job);
            return false;
        }
        logger.warn("AddCommentJob:: Job with id "+ jobId + " not found");
        return false;
    }

    @Override
    public Set<Comment> getJobComments(Long jobId) {
        if(jobRepository.existsById(jobId)) {
            return jobRepository.getCommentsOfJobId(jobId);
        }
        return null;
    }

    @Override
    public Set<JobType> getJobTypes() {
        return jobRepository.getJobTypes();
    }

    @Override
    public Set<JobStatus> getJobStatuses() {
        return jobRepository.getJobStatuses();
    }

    @Override
    public Set<SimpleJobDTO> getSimpleJobsByStatus(Long statusId) {
        return jobRepository.getJobsOfStatus(statusId);
    }
}
