package com.personal.gestao.utils.validation;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.function.Function;

public class ValidationUtils {

    public static void validateRequiredField(String value, String fieldLabel) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldLabel + " is mandatory");
        }
    }

    public static <T> void validateDuplicateOnCreate(String value,
                                          String fieldName,
                                          Function<String, Optional<T>> finder
    ) {
        finder.apply(value).ifPresent(existing -> {
            throw new DataIntegrityViolationException(fieldName + " already exists");
        });
    }

    public static <T> void validateDuplicateOnUpdate(String value,
                                          Long currentId,
                                          String fieldName,
                                          Function<String, Optional<T>> finder,
                                          Function<T, Long> idExtractor
    ) {
        finder.apply(value).ifPresent(existing -> {
            if (!idExtractor.apply(existing).equals(currentId)) {
                throw new DataIntegrityViolationException(fieldName + " already exists");
            }
        });
    }
}
