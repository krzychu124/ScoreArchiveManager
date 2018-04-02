package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.applicationserver.scorefilesmanager.domain.Score;
import pl.applicationserver.scorefilesmanager.domain.ScoreTitle;
import pl.applicationserver.scorefilesmanager.domain.ScoreType;
import pl.applicationserver.scorefilesmanager.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping(value = "/score")
public class ScoreController {

    private ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Score> getScore(@PathVariable Long id) {
        if (id != null) {
            Score score = scoreService.getScore(id);
            if (score != null) {
                return new ResponseEntity<>(score, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(params = {"scoreTypeId"})
    public ResponseEntity<List<Score>> getScoresByScoreType(@RequestParam("scoreTypeId") Long scoreTypeId) {
            List<Score> score = scoreService.getScoresByScoreType(scoreTypeId);
            if (score != null) {
                return new ResponseEntity<>(score, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(params = {"scoreType", "scoreTitle"})
    public ResponseEntity<List<Score>> getScoresByScoreTypeAndTitle(@RequestParam("scoreType") ScoreType scoreType,@RequestParam("scoreTitle") ScoreTitle scoreTitle) {
        if (scoreType!= null) {
            List<Score> score = scoreService.getScoresByTypeAndTitle(scoreType, scoreTitle);
            if (score != null) {
                return new ResponseEntity<>(score, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping(params = {"instrumentId", "scoreTypeId" })
    public ResponseEntity<List<Score>> getScoresByScoreTypeAndInstrument(@RequestParam("scoreTypeId") Long scoreTypeId,@RequestParam("instrumentId") Long instrumentId) {
            List<Score> score = scoreService.getScoresByInstrumentAndTScoreType(instrumentId, scoreTypeId);
            if (score != null) {
                return new ResponseEntity<>(score, HttpStatus.OK);
            }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Score>> getScoresByInstrumentAndTitle(@RequestParam("instrumentId") Long instrumentId, @RequestParam("scoreTitleId") Long scoreTitleId) {
        if (instrumentId != null && scoreTitleId != null) {
            List<Score> score = scoreService.getScoresByInstrumentAndTitle(instrumentId, scoreTitleId);
            if (score != null) {
                return new ResponseEntity<>(score, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity createScore(@RequestBody Score score) {
        Score newScore = scoreService.createScore(score);
        if (newScore != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newScore);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Score> updateScore(@RequestBody Score score, @PathVariable Long id) {
        Score newScore = scoreService.updateScore(id, score);
        if (newScore != null) {
            return new ResponseEntity<>(newScore, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeScore(@PathVariable Long id) {
        boolean deleted = scoreService.removeScore(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/types")
    public ResponseEntity<List<ScoreType>> getScoreTypes() {
        return new ResponseEntity<>(scoreService.getScoreTypes(), HttpStatus.OK);
    }
}
