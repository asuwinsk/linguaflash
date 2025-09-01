package pl.coderslab.linguaflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.linguaflash.model.Flashcard;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}
