package pl.coderslab.linguaflash.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flashcard_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer timesShown;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Warsaw")
    private LocalDateTime lastReviewed;

    @OneToOne
    @JoinColumn(name = "id")
    private Flashcard flashcard;


    @PrePersist
    void onCreate() {
        if (lastReviewed == null) {
            lastReviewed = LocalDateTime.now();
        }
    }
}
