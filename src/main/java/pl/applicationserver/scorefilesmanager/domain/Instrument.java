package pl.applicationserver.scorefilesmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sam_instrument")
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

    public void setId(Long id) {
        this.id = id;
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
