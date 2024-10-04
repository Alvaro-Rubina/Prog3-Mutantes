package org.alvarub.mutantes.service;

import org.alvarub.mutantes.model.dto.HumanDTO;
import org.alvarub.mutantes.model.entity.Human;
import org.alvarub.mutantes.repository.HumanRepository;
import org.alvarub.mutantes.utils.exceptions.DnaNotValidException;
import org.alvarub.mutantes.utils.mapper.HumanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanService {

    @Autowired
    private HumanRepository humanRepo;

    @Autowired
    private HumanMapper humanMapper;

    public boolean saveHuman(HumanDTO humanDTO) {
        validateDna(humanDTO.dna());

        boolean isMutant = verifyMutant(humanDTO.dna());

        Human human = humanMapper.humanDTOToHuman(humanDTO);
        human.setMutant(isMutant);

        humanRepo.save(human);

        return isMutant;
    }

    // Valida que el dna se componga de 6 strings con 6 caracteres c/u y solo contengan A, T, C y G
    private void validateDna(List<String> dna) {
        if (dna.size() != 6) {
            throw new DnaNotValidException("The DNA must have exactly 6 strands.");
        }

        for (String str : dna) {
            if (str.length() != 6) {
                throw new DnaNotValidException("Each DNA strand must be exactly 6 characters long.");

            } else if (!str.toUpperCase().matches("[ATCG]+")) {
                throw new DnaNotValidException("The DNA must only contain the letters A, T, C and G.");
            }
        }
    }

    // Cuenta las secuencias horizontales, verticales y diagonales. Para cada caso, se comparan
    // los caracteres sucesivos sin salirse de los límites de la matriz.
    private boolean verifyMutant(List<String> dna) {
        int sequences = 0;
        String[] dnaArray = dna.toArray(new String[0]);
        int length = dnaArray.length;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {

                // Horizontal
                if (j + 3 < length) {
                    if (dnaArray[i].charAt(j) == dnaArray[i].charAt(j + 1) &&
                            dnaArray[i].charAt(j) == dnaArray[i].charAt(j + 2) &&
                            dnaArray[i].charAt(j) == dnaArray[i].charAt(j + 3)) {
                        sequences++;
                        if (sequences > 1) return true;
                    }
                }

                // Vertical
                if (i + 3 < length) {
                    if (dnaArray[i].charAt(j) == dnaArray[i + 1].charAt(j) &&
                            dnaArray[i].charAt(j) == dnaArray[i + 2].charAt(j) &&
                            dnaArray[i].charAt(j) == dnaArray[i + 3].charAt(j)) {
                        sequences++;
                        if (sequences > 1) return true;
                    }
                }

                // Diagonal
                if (i + 3 < length && j + 3 < length) {
                    if (dnaArray[i].charAt(j) == dnaArray[i + 1].charAt(j + 1) &&
                            dnaArray[i].charAt(j) == dnaArray[i + 2].charAt(j + 2) &&
                            dnaArray[i].charAt(j) == dnaArray[i + 3].charAt(j + 3)) {
                        sequences++;
                        if (sequences > 1) return true;
                    }
                }

                // Diagonal inversa
                if (i + 3 < length && j - 3 >= 0) {
                    if (dnaArray[i].charAt(j) == dnaArray[i + 1].charAt(j - 1) &&
                            dnaArray[i].charAt(j) == dnaArray[i + 2].charAt(j - 2) &&
                            dnaArray[i].charAt(j) == dnaArray[i + 3].charAt(j - 3)) {
                        sequences++;
                        if (sequences > 1) return true;
                    }
                }
            }
        }

        return false;
    }
}
