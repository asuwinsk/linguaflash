package pl.coderslab.linguaflash.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.linguaflash.repository.DeckTagRepository;
import pl.coderslab.linguaflash.service.DeckTagService;

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

}
