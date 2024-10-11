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
        if (dna == null){
            throw new NullPointerException("El ADN no puede ser nulo.");

        }

        int size = dna.size();
        if (dna.size() < 4) {
            throw new DnaNotValidException("El ADN debe tener al menos 4 filas.");
        }

        for (String str : dna) {
            if (str.length() != size) {
                throw new DnaNotValidException("Cada fila del ADN debe tener la misma longitud que el número de filas.");

            } else if (!str.toUpperCase().matches("[ATCG]+")) {
                throw new DnaNotValidException("El ADN solo puede contener los caracteres A, T, C y G.");
            }
        }
    }

    //
    public boolean verifyMutant(List<String> dna) {
        int sequences = 0;
        String[] dnaArray = dna.toArray(new String[0]);
        int length = dnaArray.length;

        // Horizontal
        for (int fil = 0; fil < length; fil++) {
            for (int col = 0; col < (length / 2); col++) {
                char currentChar = dnaArray[fil].charAt(col);

                if (currentChar == dnaArray[fil].charAt(col + 1) &&
                        currentChar == dnaArray[fil].charAt(col + 2) &&
                        currentChar == dnaArray[fil].charAt(col + 3)) {

                    sequences++;
                    System.out.println("Secuencia horizontal encontrada en: (" + fil + ", " + col + ")");

                    break;
                }
                if (sequences > 1) return true;
            }
        }

        // Vertical
        for (int col = 0; col < length; col++) {
            for (int fil = 0; fil < (length / 2); fil++) {
                char currentChar = dnaArray[fil].charAt(col);

                if (currentChar == dnaArray[fil + 1].charAt(col) &&
                        currentChar == dnaArray[fil + 2].charAt(col) &&
                        currentChar == dnaArray[fil + 3].charAt(col)) {

                    sequences++;
                    System.out.println("Secuencia vertical encontrada en: (" + fil + ", " + col + ")");
                    
                    break;
                }
                if (sequences > 1) return true;
            }
        }

        // Set que guarda las posiciones que ya forman parte de una secuencia diagonal
        Set<String> usedPosition = new HashSet<>();

        // Diagonal (de izquierda a derecha)
        for (int fil = 0; fil < (length / 2); fil++) {
            for (int col = 0; col < (length / 2); col++) {

                // Si la psicion ya fue usada se salta
                if (usedPosition.contains(fil + "," + col)) continue;

                char currentChar = dnaArray[fil].charAt(col);
                if (currentChar == dnaArray[fil + 1].charAt(col + 1) &&
                        currentChar == dnaArray[fil + 2].charAt(col + 2) &&
                        currentChar == dnaArray[fil + 3].charAt(col + 3)) {

                    sequences++;
                    System.out.println("Secuencia diagonal (izquierda a derecha) encontrada en: (" + fil + ", " + col + ")");

                    // Se guardan las posiciones de la secuencia
                    for (int i = 0; i < 4; i++) {
                        usedPosition.add((fil + i) + "," + (col + i));
                    }

                    if (sequences > 1) return true;
                }
            }
        }

        // Diagonal (de derecha a izquierda)
        for (int fil = 0; fil < (length / 2); fil++) {
            for (int col = (length / 2); col < length; col++) {

                // Si la posicion ya fue usada se salta
                if (usedPosition.contains(fil + "," + col)) continue;

                char currentChar = dnaArray[fil].charAt(col);
                if (currentChar == dnaArray[fil + 1].charAt(col - 1) &&
                        currentChar == dnaArray[fil + 2].charAt(col - 2) &&
                        currentChar == dnaArray[fil + 3].charAt(col - 3)) {

                    sequences++;
                    System.out.println("Secuencia diagonal (derecha a izquierda) encontrada en: (" + fil + ", " + col + ")");

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
