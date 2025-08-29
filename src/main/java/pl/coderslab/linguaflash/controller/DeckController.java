package pl.coderslab.linguaflash.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.linguaflash.model.Deck;
import pl.coderslab.linguaflash.model.Flashcard;
import pl.coderslab.linguaflash.service.DeckService;
import jakarta.validation.Valid;
import pl.coderslab.linguaflash.service.FlashcardService;

import java.util.List;

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
    public List<Deck> getAllDecks() {
        return deckService.findAll();
    }

    // find by id
    @GetMapping("/{id}")
    public Deck getDeckById(@PathVariable Long id) {
        return deckService.findById(id);
    }

    // create a new deck
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public String createDeck(@RequestBody @Valid Deck deck) {
        deckService.save(deck);
        return "Deck created successfully";
    }

    // update an existing deck
    @PutMapping("/update/{id}")
    public String updateDeck(@PathVariable Long id, @RequestBody @Valid Deck deck) {
        Deck existingDeck = deckService.findById(id);
        if (existingDeck == null) {
            return "Deck not found";
        }
        deck.setName(deck.getName());
        deck.setDescription(deck.getDescription());
        deck.setDateCreated(existingDeck.getDateCreated());
        deck.setTargetLanguage(deck.getTargetLanguage());
        deck.setSourceLanguage(deck.getSourceLanguage());
        deck.setColor(deck.getColor());
//        deck.setDeckTag(deck.getDeckTag());
        deckService.save(deck);
        return "Deck updated successfully";
    }

    // add flashcards to a deck
    @PutMapping("/update/{id}/add/flashcards/{idCard}")
    public String addFlashcards(@PathVariable Long id, @PathVariable Long idCard) {
        Deck existingDeck = deckService.findById(id);
        if (existingDeck == null) {
            return "Deck not found";
        }

        Flashcard flashcard = flashcardService.findById(idCard);
        if (flashcard == null) {
            return "Flashcard not found";
        }

        List<Flashcard> cardsInDeck = existingDeck.getFlashcards();
        boolean alreadyExists = cardsInDeck.stream()
                .anyMatch(card -> card.getId().equals(idCard));
        if (alreadyExists) {
            return "Flashcard already in deck";
        }

        cardsInDeck.add(flashcard);
        deckService.save(existingDeck);
        return "Flashcard added to a deck.";
    }

    //remove flashcard from a deck
    @PutMapping("/update/{id}/remove/flashcards/{idCard}")
    public String removeFlashcards(@PathVariable Long id, @PathVariable Long idCard) {
        Deck existingDeck = deckService.findById(id);
        if (existingDeck == null) {
            return "Deck not found";
        }

        Flashcard flashcard = flashcardService.findById(idCard);
        if (flashcard == null) {
            return "Flashcard not found";
        }

        List<Flashcard> cardsInDeck = existingDeck.getFlashcards();
        boolean exists = cardsInDeck.stream()
                .anyMatch(card -> card.getId().equals(idCard));
        if (!exists) {
            return "Flashcard not in the deck";
        }

        cardsInDeck.remove(flashcard);
        deckService.save(existingDeck);
        return "Flashcard removed from the deck.";
    }

    // delete a deck
    @DeleteMapping("/delete/{id}")
    public String deleteDeck(@PathVariable Long id) {
        Deck deck = deckService.findById(id);
        deckService.remove(deck);
        return "Deck deleted successfully";
    }

}
