package pl.training.shop.products.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.Grid;
import lombok.Getter;
import pl.training.shop.products.model.Product;

public class ProductSelectedEvent extends ComponentEvent<Grid<Product>> {

    @Getter
    private final Product product;

    public ProductSelectedEvent(Grid<Product> source, Product product) {
        super(source, false);
        this.product = product;
    }

}
