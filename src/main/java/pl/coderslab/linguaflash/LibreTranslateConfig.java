package pl.coderslab.linguaflash;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class LibreTranslateConfig {

    @Value("${libretranslate.api-url}")
    private String apiUrl;

    @Bean
    public WebClient libreTranslateWebClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .build();
    }
}
