package pl.coderslab.linguaflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.linguaflash.model.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
}
