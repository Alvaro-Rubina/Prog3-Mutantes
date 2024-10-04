package org.alvarub.mutantes.service;

import org.alvarub.mutantes.utils.exceptions.DnaNotValidException;
import org.springframework.stereotype.Service;

import java.util.List;

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

                    // Acá se procura no volver a contar la misma secuencia
                    while (col + 1 < length && dnaArray[fil].charAt(col + 1) == currentChar) {
                        col++;
                    }

                    if (sequences > 1) return true;
                }
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

                    // Acá se procura no volver a contar la misma secuencia
                    while (fil + 1 < length && dnaArray[fil + 1].charAt(col) == currentChar) {
                        fil++;
                    }

                    if (sequences > 1) return true;
                }
            }
        }

        // Diagonal (de izquierda a derecha)
        for (int fil = 0; fil < length - 3; fil++) {
            for (int col = 0; col < length - 3; col++) {
                char currentChar = dnaArray[fil].charAt(col);
                if (currentChar == dnaArray[fil + 1].charAt(col + 1) &&
                        currentChar == dnaArray[fil + 2].charAt(col + 2) &&
                        currentChar == dnaArray[fil + 3].charAt(col + 3)) {

                    sequences++;
                    System.out.println("Diagonal (left to right) sequence found at: (" + fil + ", " + col + ")");

                    // Acá se procura no volver a contar la misma secuencia
                    while (fil + 1 < length && col + 1 < length &&
                            dnaArray[fil + 1].charAt(col + 1) == currentChar) {
                        fil++;
                        col++;
                    }

                    // Valido los límites para no salirme de la matriz
                    fil = Math.min(fil, length - 4);
                    col = Math.min(col, length - 4);

                    if (sequences > 1) return true;
                }
            }
        }

        // Diagonal (de derecha a izquierda)
        for (int fil = 0; fil < length - 3; fil++) {
            for (int col = 3; col < length; col++) {
                char currentChar = dnaArray[fil].charAt(col);
                if (currentChar == dnaArray[fil + 1].charAt(col - 1) &&
                        currentChar == dnaArray[fil + 2].charAt(col - 2) &&
                        currentChar == dnaArray[fil + 3].charAt(col - 3)) {

                    sequences++;
                    System.out.println("Diagonal (right to left) sequence found at: (" + fil + ", " + col + ")");

                    // Acá se procura no volver a contar la misma secuencia
                    while (fil + 1 < length && col - 1 >= 0 &&
                            dnaArray[fil + 1].charAt(col - 1) == currentChar) {
                        fil++;
                        col--;
                    }

                    // Valido los límites para no salirme de la matriz
                    fil = Math.min(fil, length - 4);
                    col = Math.max(col, 3);

                    if (sequences > 1) return true;
                }
            }
        }

        return false;
    }
}
