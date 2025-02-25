package app.jabafood.cleanarch.infrastructure.config;

import app.jabafood.cleanarch.application.useCases.user.*;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(IUserGateway userGateway) {
        return new CreateUserUseCase(userGateway);
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(IUserGateway userGateway) {
        return new GetUserByIdUseCase(userGateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(IUserGateway userGateway) {
        return new UpdateUserUseCase(userGateway);
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
