package pl.training.shop.products.view;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import pl.training.shop.products.model.Product;

import java.util.List;

public class ProductsGrid extends VerticalLayout {

    private final Grid<Product> grid = new Grid<>();

    public ProductsGrid() {
        initGrid();
    }

    private void initGrid() {
        grid.addColumn(Product::getName).setHeader("Name");
        grid.addColumn(Product::getDescription).setHeader("Description");
        grid.addColumn(Product::getQuantity).setHeader("Quantity");
        grid.addColumn(Product::getPrice).setHeader("Price");
        grid.asSingleSelect().addValueChangeListener(this::onProductSelected);
        add(grid);
    }

    private void onProductSelected(ValueChangeEvent<Product> event) {
        fireEvent(new ProductSelectedEvent(grid, event.getValue()));
    }

    public Registration addProductSelectedListener(ComponentEventListener<ProductSelectedEvent> listener) {
        return addListener(ProductSelectedEvent.class, listener);
    }

    public void setProducts(List<Product> products) {
        grid.setItems(products);
    }

}
