package org.alvarub.mutantes.service;

import org.alvarub.mutantes.utils.exceptions.DnaNotValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorServiceTest {


    MutantDetectorService mutantDetectorService;

    @BeforeEach
    void setUp() {
        mutantDetectorService = new MutantDetectorService();
    }

    @Test
    @DisplayName("Validar ingesando ADN válido")
    void validateDna() {
        String[] dna = {"TCTCTC", "AGAGAG", "CTCTCT", "GAGAGA", "TCTCTC", "AGAGAG"};

        assertDoesNotThrow(() -> mutantDetectorService.validateDna(dna));
    }

    @Test
    @DisplayName("Validar ingresando ADN vacío")
    void validateDnaEmpty() {
        String[] dna = {};

        Exception ex = assertThrows(DnaNotValidException.class, () -> mutantDetectorService.validateDna(dna));
        assertEquals("El ADN debe tener al menos 4 filas.", ex.getMessage());
    }

    @Test
    @DisplayName("Validar ingresando ADN nulo")
    void validateDnaNUll() {
        String[] dna = null;

        Exception ex = assertThrows(NullPointerException.class, () -> mutantDetectorService.validateDna(dna));
        assertEquals("El ADN no puede ser nulo.", ex.getMessage());
    }

    @Test
    @DisplayName("Validar ingresando un ADN NxM")
    void validateDnaNxM() {
        String[] dna = {"AAAA", "CCCC", "TCTC", "GAGA", "TTAT"}; // 4x5

        Exception ex = assertThrows(DnaNotValidException.class, () -> mutantDetectorService.validateDna(dna));
        assertEquals("Cada fila del ADN debe tener la misma longitud que el número de filas.", ex.getMessage());
    }

    @Test
    @DisplayName("Validar ingresando un ADN con números")
    void validateDnaWithNumbers() {
        String[] dna = {"9009", "1847", "2004", "2455"};

        Exception ex = assertThrows(DnaNotValidException.class, () -> mutantDetectorService.validateDna(dna));
        assertEquals("El ADN solo puede contener los caracteres A, T, C y G.", ex.getMessage());
    }

    @Test
    @DisplayName("Validar ingresando ADN con letras no permitidas")
    void validateDnaWithInvalidLetters() {
        String[] dna = {"TTACV", "VACPT", "MCATG", "AATLC", "YAACT"}; // en cada secuencia hay 1 letra no permitida

        Exception ex = assertThrows(DnaNotValidException.class, () -> mutantDetectorService.validateDna(dna));
        assertEquals("El ADN solo puede contener los caracteres A, T, C y G.", ex.getMessage());
    }

    @Test
    void verifyMutant() {
    }
}