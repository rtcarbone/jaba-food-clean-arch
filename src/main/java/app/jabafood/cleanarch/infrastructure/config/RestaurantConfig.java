package app.jabafood.cleanarch.infrastructure.config;

import app.jabafood.cleanarch.application.useCases.restaurant.*;
import app.jabafood.cleanarch.domain.repositories.RestaurantRepository;
import app.jabafood.cleanarch.interfaceAdapters.mappers.RestaurantMapper;
import app.jabafood.cleanarch.interfaceAdapters.mappers.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantConfig {

    @Bean
    public RestaurantMapper restaurantMapper(UserMapper userMapper) {
        return new RestaurantMapper(userMapper);
    }

    @Bean
    public CreateRestaurantUseCase createRestaurantUseCase(RestaurantRepository restaurantRepository) {
        return new CreateRestaurantUseCase(restaurantRepository);
    }

    @Bean
    public GetRestaurantByIdUseCase getRestaurantByIdUseCase(RestaurantRepository restaurantRepository) {
        return new GetRestaurantByIdUseCase(restaurantRepository);
    }

    @Bean
    public UpdateRestaurantUseCase updateRestaurantUseCase(RestaurantRepository restaurantRepository) {
        return new UpdateRestaurantUseCase(restaurantRepository);
    }

    @Bean
    public DeleteRestaurantUseCase deleteRestaurantUseCase(RestaurantRepository restaurantRepository) {
        return new DeleteRestaurantUseCase(restaurantRepository);
    }

    @Bean
    public ListRestaurantsUseCase listRestaurantsUseCase(RestaurantRepository restaurantRepository) {
        return new ListRestaurantsUseCase(restaurantRepository);
    }

}
