package uk.jakebaum.recipe.controllers.dto.converters;

public interface DtoConverter<ENTITY, DTO> {

  ENTITY toEntity(DTO dto);

  DTO toDto(ENTITY entity);

}
