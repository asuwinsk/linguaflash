package pl.coderslab.linguaflash.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "flashcards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String front;
    private String back;
    @Enumerated(EnumType.STRING)
    private Level level;
    @ManyToMany(mappedBy = "flashcards")
    @JsonIgnore
    private List<Deck> decks;
}
