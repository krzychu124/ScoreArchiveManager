package pl.applicationserver.scorefilesmanager.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class OtherFileMetadata extends AbstractFileMetadata {
    public OtherFileMetadata() {
        super("", null, new ScoreType(), null, "",0L, "other", ScoreFileType.OTHER);
    }
    public OtherFileMetadata(@NotNull String name, ScoreTitle scoreTitle, ScoreType scoreType, Instrument instrument, String url, @NotNull Long fileSize, String fileExtension, ScoreFileType scoreFileType) {
        super(name, scoreTitle, scoreType, instrument, url, fileSize, fileExtension, scoreFileType);
    }
}
