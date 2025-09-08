package pl.coderslab.linguaflash.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Front value cannot be null")
    @Size(max = 100, message = "Value can be at most 100 characters")
    private String front;

    @Column(length = 5)
    private String sourceLang;

    @NotBlank(message = "Back value cannot be null")
    @Size(max = 100, message = "Value can be at most 100 characters")
    private String back;

    @Column(length = 5)
    private String targetLang;

    @NotBlank(message = "Example sentence cannot be null")
    @Size(max = 255, message = "Value can be at most 255 characters")
    private String exampleSentence;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Level level;


    @PrePersist
    @PreUpdate
    void onCreateAndUpdate() {
        if (front != null) {
            front = capitalizeFirst(front);
        }
        if (back != null) {
            back = capitalizeFirst(back);
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
