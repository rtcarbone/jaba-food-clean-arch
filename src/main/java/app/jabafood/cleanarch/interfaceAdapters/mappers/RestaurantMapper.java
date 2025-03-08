package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantResponseDTO toDTO(Restaurant restaurant);

    Restaurant toDomain(RestaurantRequestDTO dto);

    List<RestaurantResponseDTO> toDTOList(List<Restaurant> restaurants);
}
