package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantMapper {

    private final UserMapper userMapper;

    public RestaurantMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public RestaurantDTO toDTO(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCuisineType(),
                restaurant.getOpeningHours(),
                userMapper.toDTO(restaurant.getOwner())
        );
    }

    public Restaurant toEntity(RestaurantDTO dto) {
        return new Restaurant(
                dto.id(),
                dto.name(),
                dto.cuisineType(),
                dto.openingHours(),
                userMapper.toEntity(dto.owner())
        );
    }

    public List<RestaurantDTO> toDTOList(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Restaurant> toEntityList(List<RestaurantDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
