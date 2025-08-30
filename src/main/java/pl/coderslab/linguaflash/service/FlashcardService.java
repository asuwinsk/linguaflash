package pl.coderslab.linguaflash.service;

import org.springframework.stereotype.Service;
import pl.coderslab.linguaflash.model.Flashcard;
import pl.coderslab.linguaflash.repository.FlashcardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {
    private final FlashcardRepository flashcardRepository;

    public FlashcardService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public Optional<Flashcard> findById(Long idCard) {

        return flashcardRepository.findById(idCard);
    }

    public List<Flashcard> findAll() {
        return flashcardRepository.findAll();
    }

    public void save(Flashcard flashcard) {
        flashcardRepository.save(flashcard);
    }

    public void remove(Flashcard flashcard) {
        flashcardRepository.delete(flashcard);
    }
}
