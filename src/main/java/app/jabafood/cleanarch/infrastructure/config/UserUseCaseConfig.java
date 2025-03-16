package app.jabafood.cleanarch.infrastructure.config;

import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import app.jabafood.cleanarch.domain.useCases.user.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(IUserGateway userGateway) {
        return new CreateUserUseCase(userGateway);
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(IUserGateway userGateway) {
        return new GetUserByIdUseCase(userGateway);
    }

    @Bean
    public UpdateUserPasswordUseCase updateUserPasswordUseCase(IUserGateway userGateway) {
        return new UpdateUserPasswordUseCase(userGateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(IUserGateway userGateway, IRestaurantGateway restaurantGateway) {
        return new UpdateUserUseCase(userGateway, restaurantGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(IUserGateway userGateway) {
        return new DeleteUserUseCase(userGateway);
    }

    @Bean
    public ListUsersUseCase listUsersUseCase(IUserGateway userGateway) {
        return new ListUsersUseCase(userGateway);
    }

}
