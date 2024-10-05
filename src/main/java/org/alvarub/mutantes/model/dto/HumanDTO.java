package org.alvarub.mutantes.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record HumanDTO(@NotBlank(message = "The 'name' field is mandatory") String name ,
                       @NotNull(message = "The 'dna' field is mandatory") List<String> dna) {
}
