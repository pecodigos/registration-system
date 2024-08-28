package com.pecodigos.registration_system.dtos;

import jakarta.validation.constraints.NotBlank;

public record QuestionDTO(@NotBlank String question) {
}
