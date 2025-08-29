package pl.coderslab.linguaflash.model;

import jakarta.persistence.*;
import lombok.*;


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
    private String exampleSentence;
    @Enumerated(EnumType.STRING)
    private Level level;
}
