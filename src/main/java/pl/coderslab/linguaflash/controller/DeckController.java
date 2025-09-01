package pl.coderslab.linguaflash.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.linguaflash.mapper.DeckDTOMapper;
import pl.coderslab.linguaflash.dto.DeckRequestDTO;
import pl.coderslab.linguaflash.dto.DeckResponseDTO;
import pl.coderslab.linguaflash.model.Deck;
import pl.coderslab.linguaflash.model.Flashcard;
import pl.coderslab.linguaflash.service.DeckService;
import jakarta.validation.Valid;
import pl.coderslab.linguaflash.service.FlashcardService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/decks")
public class DeckController {
    private final DeckService deckService;
    private final FlashcardService flashcardService;

    public DeckController(DeckService deckService, FlashcardService flashcardService) {
        this.deckService = deckService;
        this.flashcardService = flashcardService;
    }

    // showing all decks
    @GetMapping("/all")
    public List<DeckResponseDTO> getAllDecks() {
        return deckService.findAll().stream()
                .map(DeckDTOMapper::toDeckResponseDTO)
                .collect(Collectors.toList());
    }

    // find by id
    @GetMapping("/{id}")
    public Optional<DeckResponseDTO> getDeckById(@PathVariable Long id) {
        Optional<Deck> deck = deckService.findById(id);
        if (deck.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(DeckDTOMapper.toDeckResponseDTO(deck.get()));
    }

    // create a new deck
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public String createDeck(@RequestBody @Valid DeckRequestDTO deckDTO) {
        Deck deck = DeckDTOMapper.toDeckFromRequestDTO(deckDTO);
        deck.setDateCreated(LocalDateTime.now());
        deckService.save(deck);
        return "Deck created successfully";
    }

    // update an existing deck
    @PutMapping("/update/{id}")
    public String updateDeck(@PathVariable Long id, @RequestBody @Valid DeckRequestDTO deckDTO) {
        Optional<Deck> existingDeck = deckService.findById(id);
        if (existingDeck.isEmpty()) {
            return "Deck not found";
        }
        Deck existingDeckValue = existingDeck.get();
        existingDeckValue.setName(deckDTO.getName());
        existingDeckValue.setDescription(deckDTO.getDescription());
//        existingDeckValue.setTargetLanguage(deckDTO.getTargetLanguage());
//        existingDeckValue.setSourceLanguage(deckDTO.getSourceLanguage());
        existingDeckValue.setColor(deckDTO.getColor());
//        existingDeckValue.setDeckTag(deckDTO.getDeckTag());
        deckService.save(existingDeckValue);
        return "Deck updated successfully";
    }

    // add flashcards to a deck
    @PutMapping("/update/{id}/add/flashcards/{idCard}")
    public String addFlashcards(@PathVariable Long id, @PathVariable Long idCard) {
        Optional<Deck> existingDeck = deckService.findById(id);
        if (existingDeck.isEmpty()) {
            return "Deck not found";
        }

        Optional<Flashcard> flashcard = flashcardService.findById(idCard);
        if (flashcard.isEmpty()) {
            return "Flashcard not found";
        }

        List<Flashcard> cardsInDeck = existingDeck.get().getFlashcards();
        boolean alreadyExists = cardsInDeck.stream()
                .anyMatch(card -> card.getId().equals(idCard));
        if (alreadyExists) {
            return "Flashcard already in deck";
        }

        cardsInDeck.add(flashcard.get());
        deckService.save(existingDeck.get());
        return "Flashcard added to a deck.";
    }

    //remove flashcard from a deck
    @PutMapping("/update/{id}/remove/flashcards/{idCard}")
    public String removeFlashcards(@PathVariable Long id, @PathVariable Long idCard) {
        Optional<Deck> existingDeck = deckService.findById(id);
        if (existingDeck.isEmpty()) {
            return "Deck not found";
        }

        Optional<Flashcard> flashcard = flashcardService.findById(idCard);
        if (flashcard.isEmpty()) {
            return "Flashcard not found";
        }

        List<Flashcard> cardsInDeck = existingDeck.get().getFlashcards();
        boolean exists = cardsInDeck.stream()
                .anyMatch(card -> card.getId().equals(idCard));
        if (!exists) {
            return "Flashcard not in the deck";
        }

        cardsInDeck.remove(flashcard.get());
        deckService.save(existingDeck.get());
        return "Flashcard removed from the deck.";
    }

    // delete a deck
    @DeleteMapping("/delete/{id}")
    public String deleteDeck(@PathVariable Long id) {
        Optional<Deck> deck = deckService.findById(id);
        if (deck.isEmpty()) {
            return "Deck not found";
        }
        deckService.remove(deck.get());
        return "Deck deleted successfully";
    }
}
