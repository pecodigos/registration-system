package com.pecodigos.registration_system.dtos;

import jakarta.validation.constraints.NotBlank;

public record AnswerDTO(@NotBlank String answer) {
}
