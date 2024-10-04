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
    // los 3 caracteres sucesivos sin salirse de los límites de la matriz.
    private boolean verifyMutant(List<String> dna) {
        int sequences = 0;
        String[] dnaArray = dna.toArray(new String[0]);
        int length = dnaArray.length;

        // Horizontal
        for (int fil = 0; fil < length; fil++) {
            for (int col = 0; col < length; col++) {
                if (col + 3 < length) {
                    char currentChar = dnaArray[fil].charAt(col);
                    if (dnaArray[fil].charAt(col) == dnaArray[fil].charAt(col + 1) &&
                            dnaArray[fil].charAt(col) == dnaArray[fil].charAt(col + 2) &&
                            dnaArray[fil].charAt(col) == dnaArray[fil].charAt(col + 3)) {
                        sequences++;

                        while (col + 1 < length && dnaArray[fil].charAt(col + 1) == currentChar) {
                            col++;
                        }

                        if (sequences > 1) return true;
                    }
                }
            }
        }

        // Vertical
        for (int fil = 0; fil < length; fil++) {
            for (int col = 0; col < length; col++) {
                if (fil + 3 < length) {
                    char currentChar = dnaArray[fil].charAt(col);
                    if (dnaArray[fil].charAt(col) == dnaArray[fil + 1].charAt(col) &&
                            dnaArray[fil].charAt(col) == dnaArray[fil + 2].charAt(col) &&
                            dnaArray[fil].charAt(col) == dnaArray[fil + 3].charAt(col)) {
                        sequences++;

                        while (fil + 1 < length && dnaArray[fil + 1].charAt(col) == currentChar) {
                            fil++;
                        }

                        if (sequences > 1) return true;
                    }
                }
            }
        }

        // Diagonal
        for (int fil = 0; fil < length; fil++) {
            for (int col = 0; col < length; col++) {
                if (fil + 3 < length && col + 3 < length) {
                    char currentChar = dnaArray[fil].charAt(col);
                    if (dnaArray[fil].charAt(col) == dnaArray[fil + 1].charAt(col + 1) &&
                            dnaArray[fil].charAt(col) == dnaArray[fil + 2].charAt(col + 2) &&
                            dnaArray[fil].charAt(col) == dnaArray[fil + 3].charAt(col + 3)) {
                        sequences++;

                        while (fil + 1 < length && col + 1 < length &&
                                dnaArray[fil + 1].charAt(col + 1) == currentChar) {
                            fil++;
                            col++;
                        }

                        if (sequences > 1) return true;
                    }
                }
            }
        }

        // Diagonal inversa
        for (int fil = 0; fil < length; fil++) {
            for (int col = 0; col < length; col++) {
                if (fil + 3 < length && col - 3 >= 0) {
                    char currentChar = dnaArray[fil].charAt(col);
                    if (dnaArray[fil].charAt(col) == dnaArray[fil + 1].charAt(col - 1) &&
                            dnaArray[fil].charAt(col) == dnaArray[fil + 2].charAt(col - 2) &&
                            dnaArray[fil].charAt(col) == dnaArray[fil + 3].charAt(col - 3)) {
                        sequences++;

                        while (fil + 1 < length && col - 1 >= 0 &&
                                dnaArray[fil + 1].charAt(col - 1) == currentChar) {
                            fil++;
                            col--;
                        }
                        
                        if (sequences > 1) return true;
                    }
                }
            }
        }

        return false;
    }
}
