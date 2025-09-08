package pl.coderslab.linguaflash.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import pl.coderslab.linguaflash.model.Flashcard;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeckResponseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime dateCreated;
    //    private Language sourceLanguage;
//    private Language targetLanguage;
    private String color;
//    private DeckTag deckTag;
    private List<Flashcard> flashcards;
}
