package app.jabafood.cleanarch.infrastructure.config;

import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.useCases.menuItem.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemUseCaseConfig {

    @Bean
    public CreateMenuItemUseCase createMenuItemUseCase(IMenuItemGateway menuItemGateway, IRestaurantGateway restaurantGateway) {
        return new CreateMenuItemUseCase(menuItemGateway, restaurantGateway);
    }

    @Bean
    public GetMenuItemByIdUseCase getMenuItemByIdUseCase(IMenuItemGateway menuItemGateway) {
        return new GetMenuItemByIdUseCase(menuItemGateway);
    }

    @Bean
    public UpdateMenuItemUseCase updateMenuItemUseCase(IMenuItemGateway menuItemGateway) {
        return new UpdateMenuItemUseCase(menuItemGateway);
    }

    @Bean
    public DeleteMenuItemUseCase deleteMenuItemUseCase(IMenuItemGateway menuItemGateway) {
        return new DeleteMenuItemUseCase(menuItemGateway);
    }

    @Bean
    public ListMenuItemUseCase listMenuItemUseCase(IMenuItemGateway menuItemGateway) {
        return new ListMenuItemUseCase(menuItemGateway);
    }

    @Bean
    public ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase(IMenuItemGateway menuItemGateway, IRestaurantGateway restaurantGateway) {
        return new ListMenuItemsByRestaurantUseCase(menuItemGateway, restaurantGateway);
    }

}
