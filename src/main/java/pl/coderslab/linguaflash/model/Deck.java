package pl.coderslab.linguaflash.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "decks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime dateCreated;
    @ManyToOne
    @JoinColumn(name = "source_language_id")
    private Language sourceLanguage;
    @ManyToOne
    @JoinColumn(name = "target_language_id")
    private Language targetLanguage;
    @Column(length = 50)
    private String color;
    @ManyToOne
    private DeckTag deckTag;
    @ManyToMany
    @JoinTable(name = "deck_flashcards",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "flashcard_id"))
    private List<Flashcard> flashcards;
}
