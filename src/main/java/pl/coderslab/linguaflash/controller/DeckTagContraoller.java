package pl.coderslab.linguaflash.controller;

import jakarta.validation.GroupSequence;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.linguaflash.model.DeckTag;
import pl.coderslab.linguaflash.service.DeckTagService;

import java.util.List;

@RestController
@RequestMapping("/deck-tags")
public class DeckTagContraoller {
    private final DeckTagService deckTagService;

    public DeckTagContraoller(DeckTagService deckTagService) {
        this.deckTagService = deckTagService;
    }

    // show list of deck tags
    @GetMapping("/all")
    public List<DeckTag> getDeckTags() {
        return deckTagService.findAll();
    }

    // find by id
    @GetMapping("/{id}")
    public DeckTag getDeckTagById(@PathVariable Long id) {
        return deckTagService.findById(id);
    }

    // add a deck tag
    @PostMapping("/add")
    public String addDeckTag(@RequestBody DeckTag deckTag) {
        deckTagService.save(deckTag);
        return "Deck tag added successfully";
    }

    // remove a deck tag
    @DeleteMapping("/remove/{id}")
    public String removeDeckTag(@PathVariable Long id) {
        DeckTag deckTag = deckTagService.findById(id);
        if (deckTag == null) {
            return "Deck tag not found";
        }
        deckTagService.remove(deckTag);
        return "Deck tag removed successfully";
    }
}
