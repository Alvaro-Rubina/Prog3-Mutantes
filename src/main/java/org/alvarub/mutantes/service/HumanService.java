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

    @Autowired
    private MutantDetectorService mutantDetectorService;

    public boolean saveHuman(HumanDTO humanDTO) {
        mutantDetectorService.validateDna(humanDTO.dna());

        boolean isMutant = mutantDetectorService.verifyMutant(humanDTO.dna());

        Human human = humanMapper.humanDTOToHuman(humanDTO);
        human.setMutant(isMutant);

        humanRepo.save(human);

        return isMutant;
    }


}
