package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.applicationserver.scorefilesmanager.domain.Comment;
import pl.applicationserver.scorefilesmanager.domain.Job;
import pl.applicationserver.scorefilesmanager.domain.JobStatus;
import pl.applicationserver.scorefilesmanager.domain.JobType;
import pl.applicationserver.scorefilesmanager.dto.ExtendedJobDTO;
import pl.applicationserver.scorefilesmanager.dto.SimpleJobDTO;
import pl.applicationserver.scorefilesmanager.service.JobService;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/types")
    public ResponseEntity<Set<JobType>> getJobTypes() {
        return new ResponseEntity<>(jobService.getJobTypes(), OK);
    }

    @GetMapping("/statuses")
    public ResponseEntity<Set<JobStatus>> getJobStatuses() {
        return new ResponseEntity<>(jobService.getJobStatuses(), OK);
    }

    @GetMapping("/byStatus/{id}")
    public ResponseEntity<Set<SimpleJobDTO>> getJobsByStatusId(@PathVariable("id") Long statusId) {
        return new ResponseEntity<>(jobService.getSimpleJobsByStatus(statusId), OK);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<SimpleJobDTO> getJobsById(@PathVariable("jobId")Long jobId){
        SimpleJobDTO job = jobService.getSimpleJobById(jobId);
        if(job != null) {
            return new ResponseEntity<>(job, OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }
    @GetMapping("/allInfo/{jobId}")
    public ResponseEntity<ExtendedJobDTO> getFullJobInfoById(@PathVariable("jobId")Long jobId){
        ExtendedJobDTO job = jobService.getFullJobById(jobId);
        if(job != null) {
            return new ResponseEntity<>(job, OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }
    @GetMapping("/{jobId}/comments")
    public ResponseEntity<Set<Comment>> getJobComments(@PathVariable("jobId") Long jobId) {
        Set<Comment> comments = jobService.getJobComments(jobId);
        if (comments != null) {
            return new ResponseEntity<>(comments, OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job){
        return new ResponseEntity<>(jobService.createJob(job), CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Boolean> updateJob(@PathVariable("id") Long jobId, @RequestBody Job job) {
        return new ResponseEntity<>(jobService.updateJob(jobId,job), OK);
    }
}
