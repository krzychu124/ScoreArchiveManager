package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.Instrument;
import pl.applicationserver.scorefilesmanager.repository.InstrumentRepository;
import pl.applicationserver.scorefilesmanager.service.InstrumentService;

import java.util.List;

@Service
public class InstrumentServiceImpl implements InstrumentService {
    private InstrumentRepository instrumentRepository;

    @Autowired
    public InstrumentServiceImpl(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    @Override
    public List<Instrument> getAll() {
        return instrumentRepository.findAll();
    }

    @Override
    public List<Instrument> getInstruments(String name) {
        return instrumentRepository.getByName(name);
    }

    @Override
    public Instrument getInstrument(Long id) {
        return instrumentRepository.getOne(id);
    }

    @Override
    public Instrument getInstrument(String name, Byte voiceNumber) {
        return instrumentRepository.getFirstByNameAndVoiceNumber(name, voiceNumber);
    }

    @Override
    public Instrument createInstrument(Instrument instrument) {
        Instrument duplicate = instrumentRepository.getFirstByNameAndInstrumentPitchAndVoiceNumber(instrument.getName(), instrument.getInstrumentPitch(), instrument.getVoiceNumber());
        if (duplicate == null && instrumentIsValid(instrument)) {
            instrumentRepository.save(instrument);
            return instrument;
        }
        return null;
    }

    private boolean instrumentIsValid(Instrument instrument) {
        return instrument.getName() != null && instrument.getInstrumentPitch() != null && instrument.getVoiceNumber() != null;
    }

    @Override
    public boolean removeInstrument(Long id) {
        if (exists(id)) {
            instrumentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean exists(Long id) {
        return instrumentRepository.existsById(id);
    }

    @Override
    public Instrument updateInstrument(Long id, Instrument instrument) {
        Instrument updated = getInstrument(id);
        if (updated != null) {
            if (!updated.getName().equals(instrument.getName())) {
                updated.setName(instrument.getName());
            }
            if (!updated.getInstrumentPitch().equals(instrument.getInstrumentPitch())) {
                updated.setInstrumentPitch(instrument.getInstrumentPitch());
            }
            if (!updated.getVoiceNumber().equals(instrument.getVoiceNumber())) {
                updated.setVoiceNumber(instrument.getVoiceNumber());
            }
            instrumentRepository.save(updated);
            return updated;
        }
        return null;
    }
}
