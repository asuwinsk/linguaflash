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
import pl.coderslab.linguaflash.model.Flashcard;
import pl.coderslab.linguaflash.repository.DeckRepository;
import pl.coderslab.linguaflash.repository.DeckTagRepository;
import pl.coderslab.linguaflash.repository.FlashcardRepository;
import pl.coderslab.linguaflash.repository.LanguageRepository;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/view/decks")
public class DeckViewController {
    private final DeckRepository deckRepository;
    private final LanguageRepository languageRepository;
    private final DeckTagRepository deckTagRepository;
    private final FlashcardRepository flashcardRepository;

    public DeckViewController(DeckRepository deckRepository, LanguageRepository languageRepository, DeckTagRepository deckTagRepository, FlashcardRepository flashcardRepository) {
        this.deckRepository = deckRepository;
        this.languageRepository = languageRepository;
        this.deckTagRepository = deckTagRepository;
        this.flashcardRepository = flashcardRepository;
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

            // validation size of name
            if (deck.getName().length() > 100) {
                model.addAttribute("exceedLengthName", true);
            }
            // validation size of description
            if (deck.getDescription().length() > 255) {
                model.addAttribute("exceedLengthDesc", true);
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

            // validation size of name
            if (deck.getName().length() > 100) {
                model.addAttribute("exceedLengthName", true);
            }

            // validation size of description
            if (deck.getDescription().length() > 255) {
                model.addAttribute("exceedLengthDesc", true);
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

    @GetMapping("/delete/{id}")
    public String deleteDeck(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found"));
        redirectAttributes.addFlashAttribute("success", "Deck deleted successfully");
        deckRepository.delete(deck);
        return "redirect:/view/decks";
    }

    // flashcards endpoints

    @GetMapping("/{id}/flashcards")
    public String viewFlashcardsInDeck(@PathVariable Long id, Model model) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found"));

        model.addAttribute("deck", deck);
        model.addAttribute("flashcards", deck.getFlashcards());
        return "flashcards/in-deck";
    }

    @GetMapping("/{id}/flashcards/select")
    public String selectFlashcardsForm(@PathVariable Long id, Model model) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found"));

        model.addAttribute("flashcards", flashcardRepository.findAll());
        model.addAttribute("deck", deck);
        return "flashcards/select";
    }

    @PostMapping("/{id}/flashcards/select")
    public String addFlashcardsToDeck(@PathVariable Long id,
                                      @RequestParam("flashcardIds") List<Long> flashcardIds,
                                      RedirectAttributes redirectAttributes) {

        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found"));

        List<Flashcard> selected = flashcardRepository.findAllById(flashcardIds);
        deck.getFlashcards().addAll(selected);
        deckRepository.save(deck);

        redirectAttributes.addFlashAttribute("success", "Flashcards added successfully");
        return "redirect:/view/decks/" + id + "/flashcards";
    }

    @GetMapping("/{id}/flashcards/delete/{flashcardId}")
    public String deleteFlashcardFromDeck(@PathVariable Long id,
                                          @PathVariable Long flashcardId,
                                          RedirectAttributes redirectAttributes) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found"));

        Flashcard flashcardToRemove = deck.getFlashcards().stream()
                .filter(f -> f.getId().equals(flashcardId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard not found in this deck"));

        deck.getFlashcards().remove(flashcardToRemove);
        deckRepository.save(deck);

        redirectAttributes.addFlashAttribute("success", "Flashcard deleted successfully");
        return "redirect:/view/decks/" + id + "/flashcards";
    }
}
