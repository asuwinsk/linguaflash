package pl.coderslab.linguaflash.service;

import org.springframework.stereotype.Service;
import pl.coderslab.linguaflash.model.DeckTag;
import pl.coderslab.linguaflash.repository.DeckTagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DeckTagService {
    private final DeckTagRepository deckTagRepository;

    public DeckTagService(DeckTagRepository deckTagRepository) {
        this.deckTagRepository = deckTagRepository;
    }

    public List<DeckTag> findAll() {
        return deckTagRepository.findAll();
    }

    public Optional<DeckTag> findById(Long id) {
        return this.deckTagRepository.findById(id);
    }

    public void save(DeckTag deckTag) {
        deckTagRepository.save(deckTag);
    }

    public void remove(DeckTag deckTag) {
        deckTagRepository.delete(deckTag);
    }
}
