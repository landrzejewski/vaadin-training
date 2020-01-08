package pl.training.shop.products.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductService;

import java.util.List;

@Route("products")
public class ProductsView extends VerticalLayout {

    private final ProductService productService;
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private Button addButton = new Button("Add");
    private Button editButton = new Button("Edit");
    private Button removeButton = new Button("Remove");
    private final ProductsGrid productsGrid = new ProductsGrid();

    private Product selectedProduct;

    @Autowired
    private ProductsView(ProductService productService) {
        this.productService = productService;
        initButtons();
        initProductsGrid();
    }

    private void initButtons() {
        editButton.setVisible(false);
        removeButton.setVisible(false);
        buttonsLayout.add(addButton, editButton, removeButton);
        add(buttonsLayout);
    }

    private void initProductsGrid() {
        productsGrid.addProductSelectedListener(event -> onProductSelected(event.getProduct()));
        refreshProducts();
        add(productsGrid);
    }

    private void onProductSelected(Product product) {
        selectedProduct = product;
        editButton.setVisible(product != null);
        removeButton.setVisible(product != null);
    }

    private void refreshProducts() {
        List<Product> products = productService.getAllProduct();
        productsGrid.setProducts(products);
    }

}
