package org.alvarub.mutantes.service;

import org.alvarub.mutantes.utils.exceptions.DnaNotValidException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MutantDetectorService {

    // Valida que el dna se componga de 6 strings con 6 caracteres c/u y solo contengan A, T, C y G
    public void validateDna(List<String> dna) {
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

    // Se cuentan las secuencias de 4 caracteres iguales en horizontal, vertical y diagonales.
    // En cada caso, se procura no salirse de los límites de la matriz. Si se encuentran más de una secuencia, se retorna true.
    public boolean verifyMutant(List<String> dna) {
        int sequences = 0;
        String[] dnaArray = dna.toArray(new String[0]);
        int length = dnaArray.length;

        // Horizontal
        for (int fil = 0; fil < length; fil++) {
            for (int col = 0; col < length - 3; col++) {
                char currentChar = dnaArray[fil].charAt(col);

                if (currentChar == dnaArray[fil].charAt(col + 1) &&
                        currentChar == dnaArray[fil].charAt(col + 2) &&
                        currentChar == dnaArray[fil].charAt(col + 3)) {

                    sequences++;
                    System.out.println("Horizontal sequence found at: (" + fil + ", " + col + ")");

                    break;
                }
                if (sequences > 1) return true;
            }
        }

        // Vertical
        for (int col = 0; col < length; col++) {
            for (int fil = 0; fil < length - 3; fil++) {
                char currentChar = dnaArray[fil].charAt(col);

                if (currentChar == dnaArray[fil + 1].charAt(col) &&
                        currentChar == dnaArray[fil + 2].charAt(col) &&
                        currentChar == dnaArray[fil + 3].charAt(col)) {

                    sequences++;
                    System.out.println("Vertical sequence found at: (" + fil + ", " + col + ")");
                    
                    break;
                }
                if (sequences > 1) return true;
            }
        }

        // Set que guarda las posiciones que ya forman parte de una secuencia diagonal
        Set<String> usedPosition = new HashSet<>();

        // Diagonal (de izquierda a derecha)
        for (int fil = 0; fil < length - 3; fil++) {
            for (int col = 0; col < length - 3; col++) {

                // Si la psicion ya fue usada se salta
                if (usedPosition.contains(fil + "," + col)) continue;

                char currentChar = dnaArray[fil].charAt(col);
                if (currentChar == dnaArray[fil + 1].charAt(col + 1) &&
                        currentChar == dnaArray[fil + 2].charAt(col + 2) &&
                        currentChar == dnaArray[fil + 3].charAt(col + 3)) {

                    sequences++;
                    System.out.println("Diagonal (left to right) sequence found at: (" + fil + ", " + col + ")");

                    // Se guardan las posiciones de la secuencia
                    for (int i = 0; i < 4; i++) {
                        usedPosition.add((fil + i) + "," + (col + i));
                    }

                    if (sequences > 1) return true;
                }
            }
        }

        // Diagonal (de derecha a izquierda)
        for (int fil = 0; fil < length - 3; fil++) {
            for (int col = 3; col < length; col++) {

                // Si la posicion ya fue usada se salta
                if (usedPosition.contains(fil + "," + col)) continue;

                char currentChar = dnaArray[fil].charAt(col);
                if (currentChar == dnaArray[fil + 1].charAt(col - 1) &&
                        currentChar == dnaArray[fil + 2].charAt(col - 2) &&
                        currentChar == dnaArray[fil + 3].charAt(col - 3)) {

                    sequences++;
                    System.out.println("Diagonal (right to left) sequence found at: (" + fil + ", " + col + ")");

                    // Se guardan las posiciones de la secuencia
                    for (int i = 0; i < 4; i++) {
                        usedPosition.add((fil + i) + "," + (col - i));
                    }

                    if (sequences > 1) return true;
                }
            }
        }

        return false;
    }
}
