package com.pecodigos.registration_system.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentDTO(@NotBlank String name, @NotBlank String email, @NotNull Integer age, @NotNull Double height) {
}
