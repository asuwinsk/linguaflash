package pl.coderslab.linguaflash.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.linguaflash.ResourceNotFoundException;
import pl.coderslab.linguaflash.model.Flashcard;
import pl.coderslab.linguaflash.service.FlashcardService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController {
    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    // show all flashcards
    @GetMapping("/all")
    public List<Flashcard> getAllFlashcards() {
        return flashcardService.findAll();
    }

    // find by id
    @GetMapping("/{id}")
    public Flashcard getFlashcardById(@PathVariable Long id) {
        return flashcardService.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("Flashcard with id %d not found", id)));
    }

    // create a new flashcard
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public String createFlashcard(@RequestBody Flashcard flashcard) {
        flashcardService.save(flashcard);
        return "Flashcard created successfully";
    }

    // update an existing flashcard
    @PutMapping("/update/{id}")
    public String updateFlashcard(@PathVariable Long id, @RequestBody Flashcard flashcard) {
        Optional<Flashcard> existingFlashcard = flashcardService.findById(id);
        if (existingFlashcard.isEmpty()) {
            return "Flashcard not found";
        }
        flashcard.setFront(flashcard.getFront());
        flashcard.setBack(flashcard.getBack());
        flashcard.setExampleSentence(flashcard.getExampleSentence());
        flashcard.setLevel(flashcard.getLevel());
        flashcardService.save(flashcard);
        return "Flashcard updated successfully";
    }

    // remove a flashcard
    @DeleteMapping("/delete/{id}")
    public String deleteFlashcard(@PathVariable Long id) {
        Optional<Flashcard> flashcard = flashcardService.findById(id);
        if (flashcard.isEmpty()) {
            return "Flashcard not found";
        }
        flashcardService.remove(flashcard.get());
        return "Flashcard deleted successfully";
    }
}
