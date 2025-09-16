package pl.coderslab.linguaflash.mapper;


import pl.coderslab.linguaflash.dto.FlashcardRequestDTO;
import pl.coderslab.linguaflash.model.Flashcard;

public class FlashcardDTOMapper {

    public static FlashcardRequestDTO toFlashcardDTO(Flashcard flashcard) {
        return FlashcardRequestDTO.builder()
                .front(flashcard.getFront())
                .back(flashcard.getBack())
                .exampleSentence(flashcard.getExampleSentence())
                .level(flashcard.getLevel())
                .build();
    }

    public static Flashcard toFlashcard(FlashcardRequestDTO flashcardRequestDTO) {
        return Flashcard.builder()
                .front(flashcardRequestDTO.getFront())
                .back(flashcardRequestDTO.getBack())
                .exampleSentence(flashcardRequestDTO.getExampleSentence())
                .level(flashcardRequestDTO.getLevel())
                .build();
    }
}
