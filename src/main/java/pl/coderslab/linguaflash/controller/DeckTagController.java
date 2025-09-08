package pl.coderslab.linguaflash.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.linguaflash.mapper.DeckTagDTOMapper;
import pl.coderslab.linguaflash.dto.DeckTagRequestDTO;
import pl.coderslab.linguaflash.model.DeckTag;
import pl.coderslab.linguaflash.service.DeckTagService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/deck-tags")
public class DeckTagController {
    private final DeckTagService deckTagService;

    public DeckTagController(DeckTagService deckTagService) {
        this.deckTagService = deckTagService;
    }

    // show list of deck tags
    @GetMapping("/all")
    public List<DeckTag> getDeckTags() {
        return deckTagService.findAll();
    }

    // find by id
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DeckTag>> getDeckTagById(@PathVariable Long id) {
        Optional<DeckTag> deckTag = deckTagService.findById(id);
        if (deckTag.isPresent()) {
            return ResponseEntity.ok(deckTag);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // add a deck tag
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public String addDeckTag(@RequestBody DeckTagRequestDTO deckTagDTO) {
        deckTagService.save(DeckTagDTOMapper.toDeckTag(deckTagDTO));
        return "Deck tag added successfully";
    }

    // udpate a deck tag
    @PutMapping("/update/{id}")
    public String updateDeckTag(@PathVariable Long id, @RequestBody DeckTagRequestDTO deckTagDTO) {
        Optional<DeckTag> existingDeckTag = deckTagService.findById(id);
        if (existingDeckTag.isEmpty()) {
            return "Deck tag not found";
        }
        DeckTag deckTagToUpdate = existingDeckTag.get();
        deckTagToUpdate.setName(deckTagDTO.getName());
        deckTagToUpdate.setDescription(deckTagDTO.getDescription());
        deckTagService.save(deckTagToUpdate);
        return "Deck tag updated successfully";
    }

    // remove a deck tag
    @DeleteMapping("/remove/{id}")
    public String removeDeckTag(@PathVariable Long id) {
        Optional<DeckTag> deckTag = deckTagService.findById(id);
        if (deckTag.isEmpty()) {
            return "Deck tag not found";
        }
        deckTagService.remove(deckTag.get());
        return "Deck tag removed successfully";
    }
}
