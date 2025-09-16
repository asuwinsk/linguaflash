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
import pl.coderslab.linguaflash.model.DeckTag;
import pl.coderslab.linguaflash.repository.DeckTagRepository;

import java.util.Map;

@Controller
@RequestMapping("/view/decktags")
public class DeckTagViewController {

    private final DeckTagRepository deckTagRepository;

    public DeckTagViewController(DeckTagRepository deckTagRepository) {
        this.deckTagRepository = deckTagRepository;
    }

    @GetMapping
    public String listAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(defaultValue = "name") String sort,
                          @RequestParam(defaultValue = "asc") String dir,
                          Model model) {

        Map<String, String> allowedSorts = Map.of(
                "id", "id",
                "name", "name"
        );
        String sortPath = allowedSorts.getOrDefault(sort, "name");
        Sort.Direction direction = "desc".equalsIgnoreCase(dir) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortPath));
        Page<DeckTag> decktagsPage = deckTagRepository.findAll(pageable);

        model.addAttribute("decktagsPage", decktagsPage);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDir", dir);
        model.addAttribute("pageSize", size);

        return "decktags/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        DeckTag deckTag = deckTagRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("decktag", deckTag);
        return "decktags/view";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("decktag", new DeckTag());
        return "decktags/form";
    }

    @PostMapping("/add")
    public String addDeckTag(@Valid @ModelAttribute("decktag") DeckTag decktag,
                             BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            // validation missing fields
            if (decktag.getName() == null || decktag.getName().isEmpty() ||
                    decktag.getDescription() == null || decktag.getDescription().isEmpty()) {
                model.addAttribute("error", true);
            }

            // validation size of name
            if (decktag.getName().length() > 100) {
                model.addAttribute("exceedLengthName", true);
            }
            // validation size of description
            if (decktag.getDescription().length() > 255) {
                model.addAttribute("exceedLengthDesc", true);
            }
            return "decktags/form";
        }
        redirectAttributes.addFlashAttribute("success", "Deck tag added successfully");
        deckTagRepository.save(decktag);
        return "redirect:/view/decktags";
        }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        DeckTag deckTag = deckTagRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck tag not found"));
        model.addAttribute("decktag", deckTag);
        return "decktags/edit";
    }

    @PostMapping("/edit/{id}")
    public String editDeckTag(@PathVariable Long id, @Valid @ModelAttribute("decktag") DeckTag decktag,
                              BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            // validation missing fields
            if (decktag.getName() == null || decktag.getName().isEmpty() ||
                    decktag.getDescription() == null || decktag.getDescription().isEmpty()) {
                model.addAttribute("error", true);
            }

            // validation size of name
            if (decktag.getName().length() > 100) {
                model.addAttribute("exceedLengthName", true);
            }

            // validation size of description
            if (decktag.getDescription().length() > 255) {
                model.addAttribute("exceedLengthDesc", true);
            }
            return "decktags/edit";
        }
        DeckTag existing = deckTagRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck tag not found"));
        if (existing != null) {
            existing.setName(decktag.getName());
            existing.setDescription(decktag.getDescription());
            deckTagRepository.save(existing);
            redirectAttributes.addFlashAttribute("success", "Deck tag updated successfully");
        }
        return "redirect:/view/decktags";
    }

    @GetMapping("/remove/{id}")
    public String removeDeckTag(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        DeckTag deckTag = deckTagRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck tag not found"));
        redirectAttributes.addFlashAttribute("success", "Deck tag deleted successfully");
        deckTagRepository.delete(deckTag);
        return "redirect:/view/decktags";
    }
}
