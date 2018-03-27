package pl.applicationserver.scorefilesmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Byte voiceNumber;
    @NotNull
    private String instrumentPitch;

    public Instrument(){

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInstrumentPitch() {
        return instrumentPitch;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getVoiceNumber() {
        return voiceNumber;
    }

    public void setVoiceNumber(Byte voiceNumber) {
        this.voiceNumber = voiceNumber;
    }

    public void setInstrumentPitch(String instrumentPitch) {
        this.instrumentPitch = instrumentPitch;
    }
}
