package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.applicationserver.scorefilesmanager.domain.ScoreBookTitle;
import pl.applicationserver.scorefilesmanager.service.ScoreBookTitleService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/scoreBookTitle")
public class ScoreBookTitleController {

    private ScoreBookTitleService bookTitleService;

    @Autowired
    public ScoreBookTitleController(ScoreBookTitleService bookTitleService) {
        this.bookTitleService = bookTitleService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ScoreBookTitle> getScoreBookTitle(@PathVariable Long id) {
        ScoreBookTitle bookTitle = bookTitleService.getTitle(id);
        if (bookTitle != null) {
            return new ResponseEntity<>(bookTitle, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("{title}")
    public ResponseEntity<ScoreBookTitle> getScoreBookTitle(@PathVariable String title) {
        ScoreBookTitle bookTitle = bookTitleService.getTitle(title);
        if (bookTitle != null) {
            return new ResponseEntity<>(bookTitle, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<ScoreBookTitle>> getScoreBookTitles() {
        List<ScoreBookTitle> bookTitles = bookTitleService.getAll();
        return new ResponseEntity<>(bookTitles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ScoreBookTitle> addScoreBookTitle(@RequestBody ScoreBookTitle newBookTitle) {
        ScoreBookTitle bookTitle = bookTitleService.createTitle(newBookTitle);
        if (bookTitle != null) {
            return new ResponseEntity<>(bookTitle, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}")
    public ResponseEntity updateScoreBookTitle(@PathVariable Long id, @RequestBody ScoreBookTitle newTitle) {
        ScoreBookTitle bookTitle = bookTitleService.updateTitle(id, newTitle);
        if (bookTitle != null) {
            return new ResponseEntity<>(bookTitle, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeScoreBookTitle(@PathVariable Long id) {
        boolean deleted = bookTitleService.removeTitle(id);
        if (deleted) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
