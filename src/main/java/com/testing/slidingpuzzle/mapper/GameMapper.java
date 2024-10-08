package com.testing.slidingpuzzle.mapper;

import com.testing.slidingpuzzle.dto.GameDto;
import com.testing.slidingpuzzle.model.GameModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GameMapper {
    GameDto toDto(GameModel gameModel);

    List<GameDto> toDtoList(List<GameModel> gameModelList);
}
