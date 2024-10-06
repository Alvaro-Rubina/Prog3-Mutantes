package org.alvarub.mutantes.service;

import org.alvarub.mutantes.model.dto.HumanDTO;
import org.alvarub.mutantes.model.dto.StatsDTO;

public interface IHumanService {

    boolean saveHuman(HumanDTO humanDTO);
    boolean existsByDna(HumanDTO humanDTO);
    StatsDTO getStats();
}
