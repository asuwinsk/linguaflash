package pl.coderslab.linguaflash.mapper;

import pl.coderslab.linguaflash.dto.DeckRequestDTO;
import pl.coderslab.linguaflash.dto.DeckResponseDTO;
import pl.coderslab.linguaflash.model.Deck;

public class DeckDTOMapper {

    // from Deck to DeckResponseDTO
    public static DeckResponseDTO toDeckResponseDTO(Deck deck) {
        return DeckResponseDTO.builder()
                .id(deck.getId())
                .name(deck.getName())
                .description(deck.getDescription())
                .dateCreated(deck.getDateCreated())
//                .sourceLanguage(deck.getSourceLanguage() != null ? deck.getSourceLanguage().getName() : null)
//                .targetLanguage(deck.getTargetLanguage() != null ? deck.getTargetLanguage().getName() : null)
//                .deckTag(deck.getDeckTag() != null ? deck.getDeckTag().getName() : null)
                .flashcards(deck.getFlashcards())
                .build();
    }

    // from Deck to DeckRequestDTO
    public static DeckRequestDTO toDeckRequestDTO(Deck deck) {
        return DeckRequestDTO.builder()
                .name(deck.getName())
                .description(deck.getDescription())
//                .sourceLanguage(deck.getSourceLanguage() != null ? deck.getSourceLanguage().getName() : null)
//                .targetLanguage(deck.getTargetLanguage() != null ? deck.getTargetLanguage().getName() : null)
//                .deckTag(deck.getDeckTag() != null ? deck.getDeckTag().getName() : null)
                .build();
    }
    
    // from DeckResponse to Deck
    public static Deck toDeck(DeckResponseDTO deckResponseDTO) {
        return Deck.builder()
                .id(deckResponseDTO.getId())
                .name(deckResponseDTO.getName())
                .description(deckResponseDTO.getDescription())
                .dateCreated(deckResponseDTO.getDateCreated())
//                .sourceLanguage(deck.getSourceLanguage() != null ? deck.getSourceLanguage().getName() : null)
//                .targetLanguage(deck.getTargetLanguage() != null ? deck.getTargetLanguage().getName() : null)
//                .deckTag(deck.getDeckTag() != null ? deck.getDeckTag().getName() : null)
                .flashcards(deckResponseDTO.getFlashcards())
                .build();
                
    }

    // from DeckRequest to Deck
    public static Deck toDeckFromRequestDTO(DeckRequestDTO deckRequestDTO) {
        return Deck.builder()
                .name(deckRequestDTO.getName())
                .description(deckRequestDTO.getDescription())
//                .sourceLanguage(deck.getSourceLanguage() != null ? deck.getSourceLanguage().getName() : null)
//                .targetLanguage(deck.getTargetLanguage() != null ? deck.getTargetLanguage().getName() : null)
//                .deckTag(deck.getDeckTag() != null ? deck.getDeckTag().getName() : null)
                .build();

    }
}
