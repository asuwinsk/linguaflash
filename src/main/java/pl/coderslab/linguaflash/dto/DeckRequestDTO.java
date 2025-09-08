package pl.coderslab.linguaflash.dto;


import lombok.*;
import pl.coderslab.linguaflash.model.DeckTag;
import pl.coderslab.linguaflash.model.Language;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeckRequestDTO {
    private String name;
    private String description;
//    private Language sourceLanguage;
//    private Language targetLanguage;
    private String color;
//    private DeckTag deckTag;
}
