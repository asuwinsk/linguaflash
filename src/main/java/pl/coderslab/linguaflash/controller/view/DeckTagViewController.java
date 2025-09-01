package pl.coderslab.linguaflash.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.linguaflash.model.DeckTag;
import pl.coderslab.linguaflash.repository.DeckTagRepository;

@Controller
@RequestMapping("/view/decktags")
public class DeckTagViewController {

    private final DeckTagRepository deckTagRepository;

    public DeckTagViewController(DeckTagRepository deckTagRepository) {
        this.deckTagRepository = deckTagRepository;
    }

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("decktags", deckTagRepository.findAll());
        return "decktags/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("decktag", deckTagRepository.findById(id).orElse(null));
        return "decktags/view";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("decktag", new DeckTag());
        return "decktags/form";
    }

    @PostMapping("/add")
    public String addDeckTag(@ModelAttribute DeckTag decktag) {
        deckTagRepository.save(decktag);
        return "redirect:/view/decktags";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("decktag", deckTagRepository.findById(id).orElse(null));
        return "decktags/edit";
    }

    @PostMapping("/edit/{id}")
    public String editDeckTag(@PathVariable Long id, @ModelAttribute DeckTag decktag) {
        DeckTag existing = deckTagRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(decktag.getName());
            existing.setDescription(decktag.getDescription());
            deckTagRepository.save(existing);
        }
        return "redirect:/view/decktags";
    }

    @GetMapping("/remove/{id}")
    public String removeDeckTag(@PathVariable Long id) {
        deckTagRepository.deleteById(id);
        return "redirect:/view/decktags";
    }

}
