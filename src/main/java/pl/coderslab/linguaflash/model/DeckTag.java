package pl.coderslab.linguaflash.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deck_tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeckTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    private String description;
}
