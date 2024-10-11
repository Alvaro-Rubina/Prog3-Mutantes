package org.alvarub.mutantes.service;

import org.alvarub.mutantes.model.dto.HumanDTO;
import org.alvarub.mutantes.model.dto.StatsDTO;
import org.alvarub.mutantes.model.entity.Human;
import org.alvarub.mutantes.repository.HumanRepository;
import org.alvarub.mutantes.utils.exceptions.DnaAlreadyExistsException;
import org.alvarub.mutantes.utils.mapper.HumanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HumanServiceTest {

    // Mocks
    @Mock
    private HumanRepository humanRepo;

    @Mock
    private HumanMapper humanMapper;

    @Mock
    private MutantDetectorService mutantDetectorService;

    @InjectMocks
    private HumanService humanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        human = new Human();
    }

    //
    private HumanDTO humanDTO;
    private Human human = new Human();
    private StatsDTO statsDTO;

    //
    @Test
    @DisplayName("save: ADN Mutante")
    void saveHumanMutantTrue(){
        String[] dna = {"CCCC", "TATA", "GGGG", "ATAT"};
        humanDTO = new HumanDTO("Nombre", dna);

        when(humanRepo.existsByFullDna(String.join("-", humanDTO.dna()))).thenReturn(false);
        when(mutantDetectorService.verifyMutant(humanDTO.dna())).thenReturn(true);
        when(humanMapper.humanDTOToHuman(humanDTO)).thenReturn(human);

        boolean isMutant = humanService.saveHuman(humanDTO);

        assertTrue(isMutant);
        verify(humanRepo, times(1)).save(human);
    }

    @Test
    @DisplayName("save: ADN no mutante")
    void saveHumanMutantFalse() {
        String[] dna = {"CCCCCC", "TATATA", "GCGCGC", "ATATAT"};
        humanDTO = new HumanDTO("Nombre", dna);

        when(humanRepo.existsByFullDna(String.join("-", humanDTO.dna()))).thenReturn(false);
        when(mutantDetectorService.verifyMutant(humanDTO.dna())).thenReturn(false);
        when(humanMapper.humanDTOToHuman(humanDTO)).thenReturn(human);

        boolean isMutant = humanService.saveHuman(humanDTO);

        assertFalse(isMutant);
        verify(humanRepo, times(1)).save(human);
    }

    @Test
    @DisplayName("save: ADN ya existente")
    void existsByDna() {
        String[] dna = {"CCCCCC", "TATATA", "GCGCGC", "ATATAT"};
        humanDTO = new HumanDTO("Nombre", dna);

        when(humanRepo.existsByFullDna(String.join("-", humanDTO.dna()))).thenReturn(true);

        assertThrows(DnaAlreadyExistsException.class, () -> humanService.saveHuman(humanDTO));
        verify(humanRepo, times(0)).save(human);
    }

    @Test
    @DisplayName("getStats")
    void getStats() {
        when(humanRepo.countByIsMutant(true)).thenReturn(45L);
        when(humanRepo.countByIsMutant(false)).thenReturn(100L);

        statsDTO = humanService.getStats();

        assertEquals(45, statsDTO.countMutantDna());
        assertEquals(100, statsDTO.countHumanDna());
        assertEquals(0.45, statsDTO.ratio());
    }
}
