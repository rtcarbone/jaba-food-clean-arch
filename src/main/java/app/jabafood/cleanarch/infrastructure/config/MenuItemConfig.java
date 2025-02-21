package app.jabafood.cleanarch.infrastructure.config;

import app.jabafood.cleanarch.application.useCases.menuItem.*;
import app.jabafood.cleanarch.domain.repositories.MenuItemRepository;
import app.jabafood.cleanarch.interfaceAdapters.mappers.MenuItemMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemConfig {

    @Bean
    public MenuItemMapper menuItemMapper() {
        return new MenuItemMapper();
    }

    @Bean
    public CreateMenuItemUseCase createMenuItemUseCase(MenuItemRepository menuItemRepository) {
        return new CreateMenuItemUseCase(menuItemRepository);
    }

    @Bean
    public GetMenuItemByIdUseCase getMenuItemByIdUseCase(MenuItemRepository menuItemRepository) {
        return new GetMenuItemByIdUseCase(menuItemRepository);
    }

    @Bean
    public UpdateMenuItemUseCase updateMenuItemUseCase(MenuItemRepository menuItemRepository) {
        return new UpdateMenuItemUseCase(menuItemRepository);
    }

    @Bean
    public DeleteMenuItemUseCase deleteMenuItemUseCase(MenuItemRepository menuItemRepository) {
        return new DeleteMenuItemUseCase(menuItemRepository);
    }

    @Bean
    public ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase(MenuItemRepository menuItemRepository) {
        return new ListMenuItemsByRestaurantUseCase(menuItemRepository);
    }

}
