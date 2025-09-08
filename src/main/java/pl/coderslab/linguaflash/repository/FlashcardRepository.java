package pl.coderslab.linguaflash.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.linguaflash.model.Flashcard;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    @Query(value = "select f from Deck d join d.flashcards f where d.id = :deckId",
            countQuery = "select count(f) from Deck d join d.flashcards f where d.id = :deckId")
    Page<Flashcard> findByDeckId(@Param("deckId") Long deckId, Pageable pageable);
}
