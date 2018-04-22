package pl.applicationserver.scorefilesmanager.domain;

import javax.persistence.*;

@Entity
@Table(name = "sam_jobtype")
public class JobType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String name_pl;

    public JobType() {
    }

    public Long getId() {
        return id;
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

    public String getName_pl() {
        return name_pl;
    }

    public void setName_pl(String name_pl) {
        this.name_pl = name_pl;
    }
}
