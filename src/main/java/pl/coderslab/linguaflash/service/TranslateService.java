package pl.coderslab.linguaflash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.linguaflash.model.Translation;
import pl.coderslab.linguaflash.repository.TranslationRepository;

@Service
@RequiredArgsConstructor
public class TranslateService {

    private final TranslationRepository translationRepository;

    public String translate(String text, String sourceLang, String targetLang) {
        return translationRepository
                .findBySourceTextIgnoreCaseAndSourceLangAndTargetLang(text, sourceLang, targetLang)
                .map(Translation::getTargetText)
                .orElse("[no translation]");
    }
}
