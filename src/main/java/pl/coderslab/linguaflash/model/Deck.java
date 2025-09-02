package pl.coderslab.linguaflash.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.coderslab.linguaflash.validation.DifferentLanguages;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "decks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DifferentLanguages
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be null")
    private String name;
    @NotBlank(message = "Description cannot be null")
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Warsaw")
    private LocalDateTime dateCreated;
    @ManyToOne
    @JoinColumn(name = "source_language_id")
    @NotNull
    private Language sourceLanguage;
    @ManyToOne
    @JoinColumn(name = "target_language_id")
    @NotNull
    private Language targetLanguage;
    @Column(length = 50)
    private String color;
    @ManyToOne
    @NotNull
    private DeckTag deckTag;
    @ManyToMany
    @JoinTable(name = "deck_flashcards",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "flashcard_id"))
    private List<Flashcard> flashcards;

    @PrePersist
    void onCreate() {
        if (dateCreated == null) {
            dateCreated = LocalDateTime.now();
        }
    }
}
