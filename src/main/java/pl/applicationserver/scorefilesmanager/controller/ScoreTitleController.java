package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.applicationserver.scorefilesmanager.domain.ScoreTitle;
import pl.applicationserver.scorefilesmanager.service.ScoreTitleService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/scoreTitle")
public class ScoreTitleController {
    private ScoreTitleService scoreTitleService;

    @Autowired
    public ScoreTitleController(ScoreTitleService scoreTitleService) {
        this.scoreTitleService = scoreTitleService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ScoreTitle> getScoreTitle(@PathVariable Long id) {
        ScoreTitle bookTitle = scoreTitleService.getTitle(id);
        if (bookTitle != null) {
            return new ResponseEntity<>(bookTitle, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/scoreType/")
    public ResponseEntity<Set<ScoreTitle>> getScoreTitlesByBookTitle(@RequestParam Long scoreTypeId) {
        Set<ScoreTitle> titles = scoreTitleService.getAllByScoreType(scoreTypeId);
        if (titles != null) {
            return new ResponseEntity<>(titles, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<ScoreTitle>> getScoreTitles() {
        List<ScoreTitle> titles = scoreTitleService.getAll();
        return new ResponseEntity<>(titles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ScoreTitle> addScoreTitle(@RequestBody ScoreTitle newBookTitle) {
        ScoreTitle scoreTitle = scoreTitleService.createTitle(newBookTitle);
        if (scoreTitle != null) {
            return new ResponseEntity<>(scoreTitle, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}")
    public ResponseEntity updateScoreTitle(@PathVariable Long id, @RequestBody ScoreTitle newTitle) {
        ScoreTitle scoreTitle = scoreTitleService.updateTitle(id, newTitle);
        if (scoreTitle != null) {
            return new ResponseEntity<>(scoreTitle, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeScoreTitle(@PathVariable Long id) {
        if (scoreTitleService.exists(id)) {
            boolean deleted = scoreTitleService.removeTitle(id);
            if (deleted) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
