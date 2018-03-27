package pl.applicationserver.scorefilesmanager.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class PdfFileMetadata extends AbstractFileMetadata {
    public PdfFileMetadata(){
        super("NoFileName",  null, new ScoreType(), null, "NoUrl",0L, "file", ScoreFileType.OTHER);
    }

    public PdfFileMetadata(@NotNull String name, ScoreTitle scoreTitle, ScoreType scoreType, Instrument instrument, String url, @NotNull Long fileSize, String fileExtension, ScoreFileType scoreFileType) {
        super(name, scoreTitle, scoreType, instrument, url, fileSize, fileExtension, scoreFileType);
    }
}
