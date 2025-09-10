package pl.coderslab.linguaflash.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "deck_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeckStats {

    @Id
    private Long id;

    // number of times flashcards learned in deck
    // number of user's click to button 'Let's learn!' [GET endpoint view/decks/{id}/learn]
    private Integer timesLearned;

    // date of the last learn of flashcards in deck
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Warsaw")
    private LocalDateTime lastLearned;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Deck deck;

}
