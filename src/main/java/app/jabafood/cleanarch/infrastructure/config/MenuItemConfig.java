package app.jabafood.cleanarch.infrastructure.config;

import app.jabafood.cleanarch.application.useCases.menuItem.*;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemConfig {

    @Bean
    public CreateMenuItemUseCase createMenuItemUseCase(IMenuItemGateway menuItemGateway) {
        return new CreateMenuItemUseCase(menuItemGateway);
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
    public ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase(IMenuItemGateway menuItemGateway) {
        return new ListMenuItemsByRestaurantUseCase(menuItemGateway);
    }

}
