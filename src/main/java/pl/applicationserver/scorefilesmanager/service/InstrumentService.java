package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.domain.Instrument;

import java.util.List;

public interface InstrumentService {

    List<Instrument> getAll();
    List<Instrument> getInstruments(String name);
    Instrument getInstrument(Long id);
    Instrument getInstrument(String name, Byte voiceNumber);
    Instrument createInstrument(Instrument instrument);
    boolean removeInstrument(Long id);
    boolean exists(Long id);
    Instrument updateInstrument(Long id, Instrument instrument);
}
