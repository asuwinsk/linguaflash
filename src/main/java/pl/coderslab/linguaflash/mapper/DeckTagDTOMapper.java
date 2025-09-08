package pl.coderslab.linguaflash.mapper;

import pl.coderslab.linguaflash.dto.DeckTagRequestDTO;
import pl.coderslab.linguaflash.model.Deck;
import pl.coderslab.linguaflash.model.DeckTag;

public class DeckTagDTOMapper {

    public static DeckTagRequestDTO toDeckTagDTO(Deck deck) {
        return DeckTagRequestDTO.builder()
                .name(deck.getName())
                .description(deck.getDescription())
                .build();
    }

    public static DeckTag toDeckTag(DeckTagRequestDTO deckTagRequestDTO) {
        return DeckTag.builder()
                .name(deckTagRequestDTO.getName())
                .description(deckTagRequestDTO.getDescription())
                .build();
    }
}
