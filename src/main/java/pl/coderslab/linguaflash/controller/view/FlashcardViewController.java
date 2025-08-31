package pl.coderslab.linguaflash.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.linguaflash.model.Flashcard;
import pl.coderslab.linguaflash.repository.FlashcardRepository;

@Controller
@RequestMapping("/view/flashcards")
public class FlashcardViewController {
    private final FlashcardRepository flashcardRepository;

    public FlashcardViewController(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("flashcards", flashcardRepository.findAll());
        return "flashcards/list";
    }

    @GetMapping("/{id}")
    public String viewById(@PathVariable Long id, Model model) {
        model.addAttribute("flashcard", flashcardRepository.findById(id).orElse(null));
        return "flashcards/view";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("flashcard", new Flashcard());
        return "flashcards/form";
    }

    @PostMapping("/add")
    public String addFlashcard(@ModelAttribute Flashcard flashcard) {
        flashcardRepository.save(flashcard);
        return "redirect:/view/flashcards";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("flashcard", flashcardRepository.findById(id).orElse(null));
        return "flashcards/edit";
    }

    @PostMapping("/edit/{id}")
    public String editFlashcard(@PathVariable Long id, @ModelAttribute Flashcard flashcard) {
        Flashcard existing = flashcardRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setFront(flashcard.getFront());
            existing.setBack(flashcard.getBack());
            flashcardRepository.save(existing);
        }
        return "redirect:/view/flashcards";
    }

    @GetMapping("/remove/{id}")
    public String removeFlashcard(@PathVariable Long id) {
        flashcardRepository.deleteById(id);
        return "redirect:/view/flashcards";
    }
}
