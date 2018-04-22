package pl.applicationserver.scorefilesmanager.domain;

import javax.persistence.Embeddable;

@Embeddable
public class CommentSource {
    public CommentSource() {
    }

    public String getSource() {

        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getIdInSource() {
        return idInSource;
    }

    public void setIdInSource(Long idInSource) {
        this.idInSource = idInSource;
    }

    private String source;
    private Long idInSource;
}
