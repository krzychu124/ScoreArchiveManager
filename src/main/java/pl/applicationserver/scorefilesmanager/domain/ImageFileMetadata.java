package pl.applicationserver.scorefilesmanager.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class ImageFileMetadata extends AbstractFileMetadata {
    public ImageFileMetadata() {
        super("", null, new ScoreType(), null, "",0L, "img", ScoreFileType.OTHER);
    }
    public ImageFileMetadata(@NotNull String name, ScoreTitle scoreTitle, ScoreType scoreType, Instrument instrument, String url, @NotNull Long fileSize,String fileExtension, ScoreFileType scoreFileType) {
        super(name, scoreTitle, scoreType, instrument, url, fileSize, fileExtension, scoreFileType);
    }
}
