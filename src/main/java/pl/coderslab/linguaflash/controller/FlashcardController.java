package pl.coderslab.linguaflash.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.linguaflash.ResourceNotFoundException;
import pl.coderslab.linguaflash.dto.FlashcardRequestDTO;
import pl.coderslab.linguaflash.mapper.FlashcardDTOMapper;
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
    public String createFlashcard(@RequestBody FlashcardRequestDTO flashcardDTO) {
        flashcardService.save(FlashcardDTOMapper.toFlashcard(flashcardDTO));
        return "Flashcard created successfully";
    }

    // update an existing flashcard
    @PutMapping("/update/{id}")
    public String updateFlashcard(@PathVariable Long id, @RequestBody FlashcardRequestDTO flashcardDTO) {
        Optional<Flashcard> existingFlashcard = flashcardService.findById(id);
        if (existingFlashcard.isEmpty()) {
            return "Flashcard not found";
        }
        Flashcard existingFlashcardValue = existingFlashcard.get();
        existingFlashcardValue.setFront(flashcardDTO.getFront());
        existingFlashcardValue.setBack(flashcardDTO.getBack());
        existingFlashcardValue.setExampleSentence(flashcardDTO.getExampleSentence());
        existingFlashcardValue.setLevel(flashcardDTO.getLevel());
        flashcardService.save(existingFlashcardValue);
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
