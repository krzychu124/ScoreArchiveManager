package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.applicationserver.scorefilesmanager.domain.Instrument;

import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    List<Instrument> getByName(String name);
    Instrument getFirstByNameAndInstrumentPitchAndVoiceNumber(String name, String instrumentPitch, Byte voiceNumber);
    Instrument getFirstByNameAndVoiceNumber(String name, Byte voiceNumber);
}
