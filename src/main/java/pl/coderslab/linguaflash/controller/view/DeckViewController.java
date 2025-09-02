package pl.coderslab.linguaflash.controller.view;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import java.util.Map;


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
    public String listAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(defaultValue = "name") String sort,
                          @RequestParam(defaultValue = "desc") String dir,
                          Model model) {

        Map<String, String> allowedSorts = Map.of(
                "id", "id",
                "name", "name"
        );

        String sortPath = allowedSorts.getOrDefault(sort, "name");
        Sort.Direction direction = "desc".equalsIgnoreCase(dir) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortPath));
        Page<Deck> decksPage = deckRepository.findAll(pageable);


        model.addAttribute("decksPage", decksPage);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDir", dir);
        model.addAttribute("pageSize", size);
//        model.addAttribute("decks", deckRepository.findAll());
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

            // validation missing fields
            if (deck.getName() == null || deck.getName().isEmpty() ||
                    deck.getDescription() == null || deck.getDescription().isEmpty() ||
                    deck.getSourceLanguage() == null ||
                    deck.getTargetLanguage() == null ||
                    deck.getDeckTag() == null) {
                model.addAttribute("error", true);
            }

            // validation languages
            if (deck.getSourceLanguage() != null && deck.getTargetLanguage() != null
                    && deck.getSourceLanguage().getId() != null
                    && deck.getTargetLanguage().getId() != null
                    && deck.getSourceLanguage().getId().equals(deck.getTargetLanguage().getId())) {
                model.addAttribute("invalidLang", true);
            }
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

            // validation missing fields
            if (deck.getName() == null || deck.getName().isEmpty() ||
                deck.getDescription() == null || deck.getDescription().isEmpty() ||
                deck.getSourceLanguage() == null ||
                deck.getTargetLanguage() == null ||
                deck.getDeckTag() == null) {
                model.addAttribute("error", true);
            }

            // validation languages
            if (deck.getSourceLanguage() != null && deck.getTargetLanguage() != null
                    && deck.getSourceLanguage().getId() != null
                    && deck.getTargetLanguage().getId() != null
                    && deck.getSourceLanguage().getId().equals(deck.getTargetLanguage().getId())) {
                model.addAttribute("invalidLang", true);
            }
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
