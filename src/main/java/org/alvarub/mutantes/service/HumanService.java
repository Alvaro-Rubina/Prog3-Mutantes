package org.alvarub.mutantes.service;

import org.alvarub.mutantes.model.dto.HumanDTO;
import org.alvarub.mutantes.model.dto.StatsDTO;
import org.alvarub.mutantes.model.entity.Human;
import org.alvarub.mutantes.repository.HumanRepository;
import org.alvarub.mutantes.utils.exceptions.DnaAlreadyExistsException;
import org.alvarub.mutantes.utils.mapper.HumanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HumanService implements IHumanService {

    @Autowired
    private HumanRepository humanRepo;

    @Autowired
    private HumanMapper humanMapper;

    @Autowired
    private MutantDetectorService mutantDetectorService;

    //
    @Override
    public boolean saveHuman(HumanDTO humanDTO) {
        mutantDetectorService.validateDna(humanDTO.dna());

        if (this.existsByDna(humanDTO)){
            throw new DnaAlreadyExistsException("A human with the same DNA already exists.");
        }

        boolean isMutant = mutantDetectorService.verifyMutant(humanDTO.dna());

        Human human = humanMapper.humanDTOToHuman(humanDTO);
        human.setMutant(isMutant);

        humanRepo.save(human);
        return isMutant;
    }

    @Override
    public boolean existsByDna(HumanDTO humanDTO) {
        String fullDna = String.join("-", humanDTO.dna());
        return humanRepo.existsByFullDna(fullDna);
    }

    @Override
    public StatsDTO getStats(){
        long countMutantDna = humanRepo.countByIsMutant(true);
        long countHumanDna = humanRepo.countByIsMutant(false);
        double ratio = (countHumanDna == 0) ? 0 : (double) countMutantDna / countHumanDna;

        return new StatsDTO(countMutantDna, countHumanDna, ratio);
    }

}
