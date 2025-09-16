package pl.coderslab.linguaflash.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeckTagRequestDTO {
    private String name;
    private String description;
}
