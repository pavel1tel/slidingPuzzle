package com.testing.slidingpuzzle.mapper;

import com.testing.slidingpuzzle.dto.GameDto;
import com.testing.slidingpuzzle.model.GameModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GameMapper {
    GameModel toModel(GameDto gameDto);
    GameDto toDto(GameModel gameModel);
}
