package app.jabafood.cleanarch.infrastructure.config;

import app.jabafood.cleanarch.application.useCases.restaurant.*;
import app.jabafood.cleanarch.domain.repositories.RestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantConfig {

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
