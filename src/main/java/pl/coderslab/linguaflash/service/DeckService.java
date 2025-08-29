package pl.coderslab.linguaflash.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import pl.coderslab.linguaflash.model.Deck;
import pl.coderslab.linguaflash.repository.DeckRepository;

import java.util.List;

@Service
public class DeckService {
    private final DeckRepository deckRepository;

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public List<Deck> findAll() {
        return deckRepository.findAll();
    }

    public void save(@Valid Deck deck) {
        deckRepository.save(deck);
    }

    public Deck findById(Long id) {
        return deckRepository.findById(id).orElse(null);
    }

    public void remove(Deck deck) {
        deckRepository.delete(deck);
    }
}
