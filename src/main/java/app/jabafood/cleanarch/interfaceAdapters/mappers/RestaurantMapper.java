package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantResponseDTO toDTO(Restaurant restaurant);

    @Mapping(source = "ownerId", target = "owner.id")
    Restaurant toDomain(RestaurantRequestDTO dto);

    List<RestaurantResponseDTO> toDTOList(List<Restaurant> restaurants);
}
