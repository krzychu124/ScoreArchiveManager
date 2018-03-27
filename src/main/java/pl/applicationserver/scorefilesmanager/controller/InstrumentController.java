package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.applicationserver.scorefilesmanager.domain.Instrument;
import pl.applicationserver.scorefilesmanager.service.InstrumentService;

import java.util.List;

@RestController
@RequestMapping(value = "/instrument")
public class InstrumentController {
    private InstrumentService instrumentService;

    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @GetMapping
    public ResponseEntity<List<Instrument>> getAll() {
        List<Instrument> instruments = instrumentService.getAll();
        return new ResponseEntity<>(instruments, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Instrument> getInstrument(@PathVariable Long id) {
        Instrument instrument = instrumentService.getInstrument(id);
        if (instrument != null) {
            return new ResponseEntity<>(instrument, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/byName")
    public ResponseEntity<List<Instrument>> getInstrumentsByName(@RequestParam(name = "name", required = false) String name) {
        List<Instrument> instruments;
        if (name != null) {
            instruments = instrumentService.getInstruments(name);
        }else {
            instruments = instrumentService.getAll();
        }
        return new ResponseEntity<>(instruments, HttpStatus.OK);
    }

    @GetMapping(value = "/byName/{voiceNumber}")
    public ResponseEntity<Instrument> getInstrumentByNameAndVoice(@RequestParam String name, @PathVariable Byte voiceNumber) {
        Instrument instrument = instrumentService.getInstrument(name, voiceNumber);
        if (instrument != null) {
            return new ResponseEntity<>(instrument, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Instrument> createInstrument(@RequestBody Instrument newInstrument) {
        Instrument instrument = instrumentService.createInstrument(newInstrument);
        if (instrument != null) {
            return new ResponseEntity<>(instrument, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeInstrument(@PathVariable Long id) {
        Instrument instrument = instrumentService.getInstrument(id);
        if (instrument != null) {
            boolean deleted = instrumentService.removeInstrument(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<Instrument> updateInstrument(@PathVariable Long id, @RequestBody Instrument instrument) {
        boolean exists = instrumentService.exists(id);
        if (exists) {
            Instrument updatedInstrument = instrumentService.updateInstrument(id, instrument);
            if (updatedInstrument != null) {
                return new ResponseEntity<>(updatedInstrument, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
