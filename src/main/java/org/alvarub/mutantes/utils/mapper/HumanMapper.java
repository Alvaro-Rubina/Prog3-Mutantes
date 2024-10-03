package org.alvarub.mutantes.utils.mapper;

import org.alvarub.mutantes.model.dto.HumanDTO;
import org.alvarub.mutantes.model.entity.Human;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = HumanMapper.class)
public interface HumanMapper {

    //
    Human humanDTOToHuman(HumanDTO humanDTO);

    HumanDTO humanToHumanDTO(Human human);

}
