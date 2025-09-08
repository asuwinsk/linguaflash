package pl.coderslab.linguaflash.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "translations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sourceText;

    @Column(nullable = false, length = 5)
    private String sourceLang;

    @Column(nullable = false)
    private String targetText;

    @Column(nullable = false, length = 5)
    private String targetLang;
}
