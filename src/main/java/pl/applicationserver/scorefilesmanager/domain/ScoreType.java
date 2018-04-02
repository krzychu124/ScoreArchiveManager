package pl.applicationserver.scorefilesmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sam_score_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ScoreType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String name_pl;

    public ScoreType() {
    }

    public Long getId() {
        return id;
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
