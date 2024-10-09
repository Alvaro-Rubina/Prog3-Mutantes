package org.alvarub.mutantes.utils.mapper;

import org.alvarub.mutantes.model.dto.HumanDTO;
import org.alvarub.mutantes.model.entity.Human;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = HumanMapper.class)
public interface HumanMapper {

    //
    Human humanDTOToHuman(HumanDTO humanDTO);

    @AfterMapping
    default void setFullDna(@MappingTarget Human human) {
        human.setFullDna();
    }

}
