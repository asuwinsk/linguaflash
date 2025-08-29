package pl.coderslab.linguaflash.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.*;


@Entity
@Table(name = "deck_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeckDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer times_reviewed;
    @Min(1)
    @Max(10)
    private Double rating;
    @OneToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;
}
