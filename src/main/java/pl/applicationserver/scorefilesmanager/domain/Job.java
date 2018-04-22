package pl.applicationserver.scorefilesmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sam_job")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private JobType jobType;
    @CreatedBy
    private String creator;
    @LastModifiedBy
    private String lastModifiedBy;
    @ManyToOne
    private JobStatus jobStatus;
    @OneToMany
    private Set<SAFileMetadata> attachedFiles = new HashSet<>();
    private String description;
    @OneToMany
    private Set<Comment> comments;
    @OneToMany
    private Set<User> editors;
    @CreationTimestamp
    private Timestamp created;
    @LastModifiedDate
    private Timestamp modified;
    private boolean deleted = false;

    public Job() {
    }

    public Long getId() {
        return id;
    }

    public Set<User> getEditors() {
        return editors;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String modifiedBy) {
        this.lastModifiedBy = modifiedBy;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Set<SAFileMetadata> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(Set<SAFileMetadata> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setEditors(Set<User> editors) {
        this.editors = editors;
    }
}
