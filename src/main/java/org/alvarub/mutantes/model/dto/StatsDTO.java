package org.alvarub.mutantes.model.dto;

public record StatsDTO(long countMutantDna,
                       long countHumanDna,
                       double ratio) {
}
