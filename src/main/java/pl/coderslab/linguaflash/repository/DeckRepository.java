package pl.coderslab.linguaflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.linguaflash.model.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
}
