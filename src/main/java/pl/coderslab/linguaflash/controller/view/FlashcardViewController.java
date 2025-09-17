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
import pl.coderslab.linguaflash.model.Flashcard;
import pl.coderslab.linguaflash.repository.FlashcardRepository;

import java.util.Map;

@Controller
@RequestMapping("/view/flashcards")
public class FlashcardViewController {
    private final FlashcardRepository flashcardRepository;

    public FlashcardViewController(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    @GetMapping
    public String listAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(defaultValue = "id") String sort,
                          @RequestParam(defaultValue = "desc") String dir,
                          Model model) {

        Map<String, String> allowedSorts = Map.of(
                "id", "id",
                "front", "front",
                "back", "back"
        );

        String sortPath = allowedSorts.getOrDefault(sort, "id");
        Sort.Direction direction = "desc".equalsIgnoreCase(dir) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortPath));
        Page<Flashcard> flashcardsPage = flashcardRepository.findAll(pageable);


        model.addAttribute("flashcardsPage", flashcardsPage);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDir", dir);
        model.addAttribute("pageSize", size);

//        model.addAttribute("flashcards", flashcardRepository.findAll());
        return "flashcards/list";
    }

    @GetMapping("/{id}")
    public String viewById(@PathVariable Long id, Model model) {
        Flashcard flashcard = flashcardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard not found"));
        model.addAttribute("flashcard", flashcard);
        return "flashcards/view";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("flashcard", new Flashcard());
        return "flashcards/form";
    }

    @PostMapping("/add")
    public String addFlashcard(@Valid @ModelAttribute("flashcard") Flashcard flashcard,
                               BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {

            // validation missing fields
            if (flashcard.getFront() == null || flashcard.getFront().isEmpty() ||
                    flashcard.getBack() == null || flashcard.getBack().isEmpty() ||
                    flashcard.getExampleSentence() == null || flashcard.getExampleSentence().isEmpty() ||
                    flashcard.getLevel() == null) {
                model.addAttribute("error", true);
            }

            // validation size of front
            if (flashcard.getFront().length() > 100) {
                model.addAttribute("exceedLengthFront", true);
            }

            // validation size of back
            if (flashcard.getBack().length() > 100) {
                model.addAttribute("exceedLengthBack", true);
            }

            // validation size of description
            if (flashcard.getExampleSentence().length() > 255) {
                model.addAttribute("exceedLengthSentence", true);
            }
            return "flashcards/form";
        }
        redirectAttributes.addFlashAttribute("success", "Flashcard added successfully");
        flashcardRepository.save(flashcard);
        return "redirect:/view/flashcards";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Flashcard flashcard = flashcardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard not found"));
        model.addAttribute("flashcard", flashcard);
        return "flashcards/edit";
    }

    @PostMapping("/edit/{id}")
    public String editFlashcard(@PathVariable Long id, @Valid @ModelAttribute("flashcard") Flashcard flashcard,
                                BindingResult bindingResult, Model model,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            // validation missing fields
            if (flashcard.getFront() == null || flashcard.getFront().isEmpty() ||
                    flashcard.getBack() == null || flashcard.getBack().isEmpty() ||
                    flashcard.getExampleSentence() == null || flashcard.getExampleSentence().isEmpty() ||
                    flashcard.getLevel() == null) {
                model.addAttribute("error", true);
            }

            // validation size of front
            if (flashcard.getFront().length() > 100) {
                model.addAttribute("exceedLengthFront", true);
            }

            // validation size of back
            if (flashcard.getBack().length() > 100) {
                model.addAttribute("exceedLengthBack", true);
            }

            // validation size of description
            if (flashcard.getExampleSentence().length() > 255) {
                model.addAttribute("exceedLengthSentence", true);
            }
            return "decks/edit";
        }
        Flashcard existing = flashcardRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard not found"));
        existing.setFront(flashcard.getFront());
        existing.setBack(flashcard.getBack());
        existing.setExampleSentence(flashcard.getExampleSentence());
        existing.setLevel(flashcard.getLevel());
        flashcardRepository.save(existing);
        redirectAttributes.addFlashAttribute("success", "Flashcard updated successfully");
        return "redirect:/view/flashcards";
    }

    @GetMapping("/delete/{id}")
    public String deleteFlashcard(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Flashcard flashcard = flashcardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard not found"));
        redirectAttributes.addFlashAttribute("success", "Flashcard deleted successfully");
        flashcardRepository.delete(flashcard);
        return "redirect:/view/flashcards";
    }
}
