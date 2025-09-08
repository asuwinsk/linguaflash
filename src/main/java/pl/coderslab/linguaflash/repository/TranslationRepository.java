package pl.coderslab.linguaflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.linguaflash.model.Translation;

import java.util.Optional;

public interface TranslationRepository extends JpaRepository<Translation, Long> {

    Optional<Translation> findBySourceTextIgnoreCaseAndSourceLangAndTargetLang(
            String sourceText,
            String sourceLang,
            String targetLang
    );
}
