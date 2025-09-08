package pl.coderslab.linguaflash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.linguaflash.service.TranslateService;

import java.util.Map;

@RestController
@RequestMapping("/api/translate")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslateService translateService;

    @PostMapping
    public Map<String, String> translate(@RequestBody Map<String, String> payload) {
        String q = payload.get("q");
        String source = payload.getOrDefault("source", "en");
        String target = payload.getOrDefault("target", "pl");

        String translated = translateService.translate(q, source, target);
        return Map.of("translatedText", translated);
    }
}
