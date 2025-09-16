package pl.coderslab.linguaflash.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.linguaflash.model.Level;

@Getter
@Setter
@Builder
public class FlashcardRequestDTO {
    private String front;
    private String back;
    private String exampleSentence;
    @Enumerated(EnumType.STRING)
    private Level level;
}

