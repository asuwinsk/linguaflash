package pl.coderslab.linguaflash.controller.view;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.linguaflash.model.Deck;
import pl.coderslab.linguaflash.repository.DeckRepository;
import pl.coderslab.linguaflash.repository.DeckTagRepository;
import pl.coderslab.linguaflash.repository.LanguageRepository;


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
    public String addForm(Model model) {
        model.addAttribute("languages", languageRepository.findAll());
        model.addAttribute("deckTags", deckTagRepository.findAll());
        model.addAttribute("deck", new Deck());
        return "decks/form";
    }

    @PostMapping("/add")
    public String addDeck(@Valid @ModelAttribute("deck") Deck deck,
                          BindingResult bindingResult,
                          Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("languages", languageRepository.findAll());
            model.addAttribute("deckTags", deckTagRepository.findAll());
            model.addAttribute("error", true);
            return "decks/form";
        }
        redirectAttributes.addFlashAttribute("success", "Deck added successfully");
        deckRepository.save(deck);
        return "redirect:/view/decks";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found"));
        model.addAttribute("deck", deck);
        model.addAttribute("languages", languageRepository.findAll());
        model.addAttribute("deckTags", deckTagRepository.findAll());
        return "decks/edit";
    }

    @PostMapping("/edit/{id}")
    public String editDeck(@PathVariable Long id, @Valid @ModelAttribute("deck") Deck deck,
                           BindingResult bindingResult, Model model,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("languages", languageRepository.findAll());
            model.addAttribute("deckTags", deckTagRepository.findAll());
            model.addAttribute("error", true);
            return "decks/edit";
        }
        Deck existing = deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found"));
        existing.setName(deck.getName());
        existing.setDescription(deck.getDescription());
        existing.setSourceLanguage(deck.getSourceLanguage());
        existing.setTargetLanguage(deck.getTargetLanguage());
        existing.setDeckTag(deck.getDeckTag());
        deckRepository.save(existing);
        redirectAttributes.addFlashAttribute("success", "Deck updated successfully");
        return "redirect:/view/decks";
    }

    @GetMapping("/remove/{id}")
    public String removeDeck(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found"));
        redirectAttributes.addFlashAttribute("success", "Deck removed successfully");
        deckRepository.delete(deck);
        return "redirect:/view/decks";
    }
}
