package pl.coderslab.linguaflash.dto;


import lombok.*;

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
