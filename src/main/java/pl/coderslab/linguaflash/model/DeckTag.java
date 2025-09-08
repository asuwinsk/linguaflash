package pl.coderslab.linguaflash.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(max = 100, message = "Name can be at most 100 characters")
    @NotBlank(message = "Name cannot be null")
    private String name;

    @Column(length = 255, nullable = false)
    @Size(max = 255, message = "Description can be at most 255 characters")
    @NotBlank(message = "Description cannot be null")
    private String description;

    @PrePersist
    @PreUpdate
    void onCreateAndUpdate() {
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
