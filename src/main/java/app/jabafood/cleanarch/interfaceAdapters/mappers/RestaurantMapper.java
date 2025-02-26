package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    @Mapping(target = "owner", expression = "java(UserDTO.fromId(restaurant.getOwnerId()))")
    RestaurantDTO toDTO(Restaurant restaurant);

    @Mapping(target = "ownerId", expression = "java(dto.owner() != null ? dto.owner().id() : null)")
    Restaurant toDomain(RestaurantDTO dto);

    List<RestaurantDTO> toDTOList(List<Restaurant> restaurants);
}
