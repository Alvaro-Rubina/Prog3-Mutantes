package org.alvarub.mutantes.service;

import org.alvarub.mutantes.utils.exceptions.DnaNotValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorServiceTest {

    MutantDetectorService mutantDetectorService;

    // VALIDACIONES AL INGRESAR UN ADN
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

    // VALIDACIONES CASOS FACTIBLES (MUTANTE O NO MUTANTE)
    @Test
    @DisplayName("Mutante: 2 secuencias horizontales")
    void verifyMutantHorizontalTrue() {
        String[] dna = {"AAAA", "TCAG", "CCCC", "GGTC"};
        assertTrue(mutantDetectorService.verifyMutant(dna));
    }

    @Test
    @DisplayName("No mutante: 1 secuencia horizontal")
    void verifyMutantHorizontalFalse() {
        String[] dna = {"AAAA", "TCAG", "TACT", "GGTC"};
        assertFalse(mutantDetectorService.verifyMutant(dna));
    }

    @Test
    @DisplayName("Mutante: 2 secuencias verticales")
    void verifyMutantVerticalTrue(){
        String[] dna = {"TCTTC", "ACAAT", "ACCAG", "GCTAC", "CCGAT"};
        assertTrue(mutantDetectorService.verifyMutant(dna));
    }

    @Test
    @DisplayName("No Mutante: 1 secuencia vertical")
    void verifyMutantVerticalFalse(){
        String[] dna = {"TCTTC", "ACAAT", "ACCAG", "GCTTC", "CCGAT"};
        assertFalse(mutantDetectorService.verifyMutant(dna));
    }

    @Test
    @DisplayName("Mutante: 2 secuencias diagonales IZQ a DER")
    void verifyMutantDiagonalLeftToRightTrue() {
        String[] dna = {"ACTACG", "GTCTCG", "ACAGTA", "TTCAGT", "CCTCTA", "TAGCCA"};
        assertTrue(mutantDetectorService.verifyMutant(dna));
    }

    @Test
    @DisplayName("No Mutante: 1 secuencia diagonal IZQ a DER")
    void verifyMutantDiagonalLeftToRightFalse() {
        String[] dna = {"ACTACG", "GTCTCG", "ACAGTA", "TTAAGT", "CCTCTA", "TAGCCA"};
        assertFalse(mutantDetectorService.verifyMutant(dna));
    }

    @Test
    @DisplayName("Mutante: 2 secuencias diagonales DER a IZQ")
    void verifyMutantDiagonalRightToLeftTrue() {
        String[] dna = {"CCTAGC", "TGAGTG", "GATACA", "AGATCC", "TATCAT", "AATGTC"};
        assertTrue(mutantDetectorService.verifyMutant(dna));
    }

    @Test
    @DisplayName("No Mutante: 1 secuencia diagonal DER a IZQ")
    void verifyMutantDiagonalRightToLeftFalse() {
        String[] dna = {"CCTAGC", "TGAGTG", "GATACA", "AGATCC", "TTTCAT", "AATGTC"};
        assertFalse(mutantDetectorService.verifyMutant(dna));
    }
}