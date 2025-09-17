package pl.coderslab.linguaflash.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.coderslab.linguaflash.model.Deck;

import java.util.Objects;

public class DifferentLanguagesValidator implements ConstraintValidator<DifferentLanguages, Deck> {

    @Override
    public boolean isValid(Deck value, ConstraintValidatorContext context) {
        if (value == null) return true;
        if (value.getSourceLanguage() == null || value.getTargetLanguage() == null) return true;
        boolean ok = !Objects.equals(
                value.getSourceLanguage().getId(),
                value.getTargetLanguage().getId()
        );
        if (!ok) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("targetLanguage")
                    .addConstraintViolation();
        }
        return ok;
    }
}