package pl.coderslab.linguaflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.linguaflash.model.DeckTag;

@Repository
public interface DeckTagRepository extends JpaRepository<DeckTag, Long> {
}
