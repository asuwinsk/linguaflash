package pl.coderslab.linguaflash.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.linguaflash.model.Deck;
import pl.coderslab.linguaflash.model.Flashcard;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    @Query(value = "select f from Deck d join d.flashcards f where d.id = :deckId",
            countQuery = "select count(f) from Deck d join d.flashcards f where d.id = :deckId")
    Page<Flashcard> findByDeckId(@Param("deckId") Long deckId, Pageable pageable);

//    @Query(value = "select f from Flashcard f where " +
//            "(f.sourceLang = :sourceLang or :sourceLang is null OR :sourceLang = '') and " +
//            "(f.targetLang = :targetLang or :targetLang is null OR :targetLang = '')",
//            countQuery = "select count(f) from Flashcard f where f.sourceLang = :sourceLang and f.targetLang = :targetLang")
//    Page<Flashcard> findBySourceLangAndTargetLang(@Param("sourceLang") String sourceLang, @Param("targetLang") String targetLang, Pageable pageable);

    @Query(value = "select f from Flashcard f where " +
            "(:sourceLang is null or :sourceLang = '' or f.sourceLang = :sourceLang) and " +
            "(:targetLang is null or :targetLang = '' or f.targetLang = :targetLang) and " +
            "f.id not in (select fc.id from Deck d join d.flashcards fc where d.id = :deckId)",
            countQuery = "select count(f) from Flashcard f where " +
                    "(:sourceLang is null or :sourceLang = '' or f.sourceLang = :sourceLang) and " +
                    "(:targetLang is null or :targetLang = '' or f.targetLang = :targetLang) and " +
                    "f.id not in (select fc.id from Deck d join d.flashcards fc where d.id = :deckId)")
    Page<Flashcard> findAvailableFlashcardsForDeck(
            @Param("deckId") Long deckId,
            @Param("sourceLang") String sourceLang,
            @Param("targetLang") String targetLang,
            Pageable pageable
    );

}
