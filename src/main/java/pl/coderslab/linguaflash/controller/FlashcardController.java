package pl.coderslab.linguaflash.controller;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.linguaflash.model.Flashcard;
import pl.coderslab.linguaflash.service.FlashcardService;

import java.util.List;

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
        return flashcardService.findById(id);
    }

    // create a new flashcard
    @PostMapping("/create")
    public String createFlashcard(@RequestBody Flashcard flashcard) {
        flashcardService.save(flashcard);
        return "Flashcard created successfully";
    }

    // update an existing flashcard
    @PutMapping("/update/{id}")
    public String updateFlashcard(@PathVariable Long id, @RequestBody Flashcard flashcard) {
        Flashcard existingFlashcard = flashcardService.findById(id);
        if (existingFlashcard == null) {
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
        Flashcard flashcard = flashcardService.findById(id);
        if (flashcard == null) {
            return "Flashcard not found";
        }
        flashcardService.remove(flashcard);
        return "Flashcard deleted successfully";
    }
}
