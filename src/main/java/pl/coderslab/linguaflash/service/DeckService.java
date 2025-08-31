package pl.coderslab.linguaflash.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.coderslab.linguaflash.model.Deck;
import pl.coderslab.linguaflash.repository.DeckRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Deck> findById(Long id) {
        return deckRepository.findById(id);
    }

    public void remove(Deck deck) {
        deckRepository.delete(deck);
    }

}
