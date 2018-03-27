package pl.applicationserver.scorefilesmanager.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ScoreBookTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String name;

    public ScoreBookTitle() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
