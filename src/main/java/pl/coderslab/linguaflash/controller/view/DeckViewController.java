package pl.coderslab.linguaflash.controller.view;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.linguaflash.model.Deck;
import pl.coderslab.linguaflash.repository.DeckRepository;
import pl.coderslab.linguaflash.repository.DeckTagRepository;
import pl.coderslab.linguaflash.repository.LanguageRepository;
import java.time.LocalDateTime;


@Controller
@RequestMapping("/view/decks")
public class DeckViewController {
    private final DeckRepository deckRepository;
    private final LanguageRepository languageRepository;
    private final DeckTagRepository deckTagRepository;

    public DeckViewController(DeckRepository deckRepository, LanguageRepository languageRepository, DeckTagRepository deckTagRepository) {
        this.deckRepository = deckRepository;
        this.languageRepository = languageRepository;
        this.deckTagRepository = deckTagRepository;
    }

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("decks", deckRepository.findAll());
        return "decks/list";
    }

    @GetMapping("/{id}")
    public String viewById(@PathVariable Long id, Model model) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found"));
        model.addAttribute("deck", deck);
        return "decks/view";
    }

    @GetMapping("/add")
    public String addForm(@RequestParam(name = "error", required = false) String error, Model model) {
        model.addAttribute("languages", languageRepository.findAll());
        model.addAttribute("deckTags", deckTagRepository.findAll());
        model.addAttribute("deck", new Deck());
        if (error != null) {
            model.addAttribute("error", true);
        } else {
            model.addAttribute("error", false);
        }
        return "decks/form";
    }

    @PostMapping("/add")
    public String addDeck(@ModelAttribute Deck deck) {
        if (deck.getName() == null || deck.getName().trim().isEmpty() ||
                deck.getDescription() == null || deck.getDescription().trim().isEmpty() ||
                deck.getSourceLanguage() == null ||
                deck.getTargetLanguage() == null
              || deck.getDeckTag() == null
        ) {
            return "redirect:/view/decks/add?error=true";
        }
        deck.setDateCreated(LocalDateTime.now());
        deckRepository.save(deck);
        return "redirect:/view/decks";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, @RequestParam(name = "error", required = false) String error, Model model) {
        model.addAttribute("deck", deckRepository.findById(id).orElse(null));
        model.addAttribute("languages", languageRepository.findAll());
        model.addAttribute("deckTags", deckTagRepository.findAll());
        if (error != null) {
            model.addAttribute("error", true);
        } else {
            model.addAttribute("error", false);
        }
        return "decks/edit";
    }

    @PostMapping("/edit/{id}")
    public String editDeck(@PathVariable Long id, @ModelAttribute Deck deck) {
        Deck existing = deckRepository.findById(id).orElse(null);
        if (existing != null) {
            if (deck.getName() == null || deck.getName().trim().isEmpty() ||
                    deck.getDescription() == null || deck.getDescription().trim().isEmpty() ||
                    deck.getSourceLanguage() == null ||
                    deck.getTargetLanguage() == null
                    || deck.getDeckTag() == null
            ) {
                return "redirect:/view/decks/edit/" + id + "?error=true";
            }
            existing.setName(deck.getName());
            existing.setDescription(deck.getDescription());
            existing.setSourceLanguage(deck.getSourceLanguage());
            existing.setTargetLanguage(deck.getTargetLanguage());
            existing.setDeckTag(deck.getDeckTag());
            deckRepository.save(existing);
        }
        return "redirect:/view/decks";
    }

    @GetMapping("/remove/{id}")
    public String removeDeck(@PathVariable Long id) {
        deckRepository.deleteById(id);
        return "redirect:/view/decks";
    }
}
