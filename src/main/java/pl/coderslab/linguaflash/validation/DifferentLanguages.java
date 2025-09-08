package pl.coderslab.linguaflash.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DifferentLanguagesValidator.class)
public @interface DifferentLanguages {
    String message() default "Source language and target language must be different";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
