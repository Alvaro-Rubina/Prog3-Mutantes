package org.alvarub.mutantes.service;

import org.alvarub.mutantes.utils.exceptions.DnaNotValidException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MutantDetectorService {

    // Valida que el dna se componga de 6 strings con 6 caracteres c/u y solo contengan A, T, C y G
    public void validateDna(String[] dna) {
        if (dna == null){
            throw new NullPointerException("El ADN no puede ser nulo.");
        }

        int size = dna.length;
        if (dna.length < 4) {
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
    public boolean verifyMutant(String[] dna) throws InterruptedException, ExecutionException {
        AtomicInteger sequences = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Tareas
        Callable<Boolean> horizontalTask = () -> verifyHorizontal(dna, sequences);
        Callable<Boolean> verticalTask = () -> verifyVertical(dna, sequences);
        Callable<Boolean> diagonalLTRTask = () -> verifyDiagonalLeftToRight(dna, sequences);
        Callable<Boolean> diagonalRTLTask = () -> verifyDiagonalRightToLeft(dna, sequences);

        // Ejecutando las tareas
        Future<Boolean> horizontalResult = executor.submit(horizontalTask);
        Future<Boolean> verticalResult = executor.submit(verticalTask);
        Future<Boolean> diagonalLTRResult = executor.submit(diagonalLTRTask);
        Future<Boolean> diagonalRTLResult = executor.submit(diagonalRTLTask);

        // Compruebo si se encontró más de una secuencia
        boolean isMutant = horizontalResult.get() || verticalResult.get() || diagonalLTRResult.get() || diagonalRTLResult.get();

        // Verifico si se encontraron mas de 1 secuencia
        if (sequences.get() >  1) {
            executor.shutdown();
            return true;
        }

        executor.shutdown();
        return isMutant;
    }

    // Verificar secuencias horizontales
    private boolean verifyHorizontal(String[] dna, AtomicInteger sequences) {
        int length = dna.length;

        for (int fil = 0; fil < length; fil++) {
            for (int col = 0; col <= length - 4; col++) {
                char currentChar = dna[fil].charAt(col);
                if (currentChar == dna[fil].charAt(col + 1) &&
                        currentChar == dna[fil].charAt(col + 2) &&
                        currentChar == dna[fil].charAt(col + 3)) {

                    int count = sequences.incrementAndGet();
                    System.out.println("Secuencia horizontal encontrada en: (" + fil + ", " + col + ")");
                    if (count > 1) return true;

                    col += 3;
                }
            }
        }
        return false;
    }

    // Verificar secuencias verticales
    private boolean verifyVertical(String[] dna, AtomicInteger sequences) {
        int length = dna.length;

        for (int col = 0; col < length; col++) {
            for (int fil = 0; fil <= length - 4; fil++) {
                char currentChar = dna[fil].charAt(col);
                if (currentChar == dna[fil + 1].charAt(col) &&
                        currentChar == dna[fil + 2].charAt(col) &&
                        currentChar == dna[fil + 3].charAt(col)) {

                    int count = sequences.incrementAndGet();
                    System.out.println("Secuencia vertical encontrada en: (" + fil + ", " + col + ")");
                    if (count > 1) return true;

                    fil += 3;
                }
            }
        }
        return false;
    }

    // Verificar secuencias diagonales (de izquierda a derecha)
    private boolean verifyDiagonalLeftToRight(String[] dna, AtomicInteger sequences) {
        int length = dna.length;
        Set<String> usedPosition = new HashSet<>();

        for (int fil = 0; fil < (length - 4); fil++) {
            for (int col = 0; col < (length - 4); col++) {
                if (usedPosition.contains(fil + "," + col)) continue;

                char currentChar = dna[fil].charAt(col);
                if (currentChar == dna[fil + 1].charAt(col + 1) &&
                        currentChar == dna[fil + 2].charAt(col + 2) &&
                        currentChar == dna[fil + 3].charAt(col + 3)) {

                    int count = sequences.incrementAndGet();
                    System.out.println("Secuencia diagonal (izquierda a derecha) encontrada en: (" + fil + ", " + col + ")");

                    for (int i = 0; i < 4; i++) {
                        usedPosition.add((fil + i) + "," + (col + i));
                    }

                    if (count > 1) return true;
                }
            }
        }
        return false;
    }

    // Verificar secuencias diagonales (de derecha a izquierda)
    private boolean verifyDiagonalRightToLeft(String[] dna, AtomicInteger sequences) {
        int length = dna.length;
        Set<String> usedPosition = new HashSet<>();

        for (int fil = 0; fil < (length - 4); fil++) {
            for (int col = 3; col < length; col++) {
                if (usedPosition.contains(fil + "," + col)) continue;

                char currentChar = dna[fil].charAt(col);
                if (currentChar == dna[fil + 1].charAt(col - 1) &&
                        currentChar == dna[fil + 2].charAt(col - 2) &&
                        currentChar == dna[fil + 3].charAt(col - 3)) {

                    int count = sequences.incrementAndGet();
                    System.out.println("Secuencia diagonal (derecha a izquierda) encontrada en: (" + fil + ", " + col + ")");

                    for (int i = 0; i < 4; i++) {
                        usedPosition.add((fil + i) + "," + (col - i));
                    }

                    if (count > 1) return true;
                }
            }
        }
        return false;
    }
}
