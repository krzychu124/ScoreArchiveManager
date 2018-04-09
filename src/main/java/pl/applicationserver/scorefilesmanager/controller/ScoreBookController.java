package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.applicationserver.scorefilesmanager.domain.ScoreBook;
import pl.applicationserver.scorefilesmanager.service.ScoreBookService;

import java.util.Set;

@RestController
@RequestMapping("/api/scoreBook")
public class ScoreBookController {

    private ScoreBookService scoreBookService;

    @Autowired
    public ScoreBookController(ScoreBookService scoreBookService) {
        this.scoreBookService = scoreBookService;
    }

    @GetMapping(value = "/scoreType/{scoreTypeId}")
    public ResponseEntity<Set<ScoreBook>> getByType(@PathVariable Long scoreTypeId) {
        Set<ScoreBook> allByType = scoreBookService.getAllByScoreTypeId(scoreTypeId);
        return new ResponseEntity<>(allByType, HttpStatus.OK);
    }

    @GetMapping(value = "/title/{titleId}/instrument/{instrumentId}")
    public ResponseEntity<ScoreBook> getScoreBook(@PathVariable Long titleId, @PathVariable Long instrumentId) {
        ScoreBook newScoreBook = scoreBookService.getScoreBook(titleId, instrumentId);
        if (newScoreBook != null) {
            return new ResponseEntity<>(newScoreBook, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<ScoreBook> createScoreBook(@RequestBody ScoreBook scoreBook) {
        ScoreBook newScoreBook = scoreBookService.createScoreBook(scoreBook);
        if (newScoreBook != null) {
            return new ResponseEntity<>(newScoreBook, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateScoreBook(@PathVariable Long id, @RequestBody ScoreBook scoreBook) {
        if (scoreBookService.exists(id)) {
            boolean updated = scoreBookService.updateScoreBook(id, scoreBook);
            if (updated) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeScoreBook(@PathVariable("id") Long id) {
        boolean updated = scoreBookService.removeScoreBook(id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping(value = "/{scoreBookId}/scores/{scoreId}")
    public ResponseEntity<Void> addScoreToBook(@PathVariable("scoreBookId") Long scoreBookId, @PathVariable("scoreId") Long scoreId) {
        boolean added = scoreBookService.addScoreToBook(scoreBookId, scoreId);
        if (added) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{scoreBookId}/scores/{scoreId}")
    public ResponseEntity<Void> removeScoreFromBook(@PathVariable Long scoreBookId, @PathVariable Long scoreId) {
        boolean deleted = scoreBookService.removeScoreFromBook(scoreBookId, scoreId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
