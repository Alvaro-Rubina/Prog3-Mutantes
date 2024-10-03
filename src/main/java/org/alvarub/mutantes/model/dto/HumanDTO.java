package org.alvarub.mutantes.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record HumanDTO(@NotBlank String name ,
                       @NotNull List<String> dna) {
}
