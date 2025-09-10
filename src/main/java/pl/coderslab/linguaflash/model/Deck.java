package pl.coderslab.linguaflash.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(max = 100, message = "Name can be at most 100 characters")
    private String name;

    @NotBlank(message = "Description cannot be null")
    @Size(max = 255, message = "Description can be at most 255 characters")
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
        if (name != null) {
            name = capitalizeFirst(name);
        }
        if (description != null) {
            description = description.trim();
        }
    }

    @PreUpdate
    void onUpdate() {
        if (name != null) {
            name = capitalizeFirst(name);
        }
        if (description != null) {
            description = description.trim();
        }
    }

    private static String capitalizeFirst(String input) {
        String s = input.trim();
        if (s.isEmpty()) return s;
        char first = Character.toUpperCase(s.charAt(0));
        String rest = s.substring(1).toLowerCase();
        return first + rest;
    }
}
