package pl.training.shop.products.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.shop.orders.model.Order;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductService;
import pl.training.shop.view.DisposableEventBus;

import java.util.List;

@Route("products")
public class ProductsView extends VerticalLayout {

    private static final int DEFAULT_QUANTITY = 1;

    private final ProductService productService;
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private Button addButton = new Button("Add");
    private Button editButton = new Button("Edit");
    private Button removeButton = new Button("Remove");
    private final Button addToBasketButton = new Button("Add to basket");
    private final ProductsGrid productsGrid = new ProductsGrid();

    private Product selectedProduct;
    private Order order;
    private DisposableEventBus disposableEventBus;

    @Autowired
    private ProductsView(ProductService productService, Order order, DisposableEventBus disposableEventBus) {
        this.productService = productService;
        this.order = order;
        this.disposableEventBus = disposableEventBus;
        initButtons();
        initProductsGrid();
    }

    private void initButtons() {
        UI ui = UI.getCurrent();
        addButton.addClickListener(event -> ui.navigate(AddProductView.class));
        editButton.setVisible(false);
        editButton.addClickListener(event -> onEditProduct());
        removeButton.setVisible(false);
        removeButton.addClickListener(event -> onRemoveProduct());
        addToBasketButton.setVisible(false);
        addToBasketButton.addClickListener(event -> onAddProductToBasket());
        buttonsLayout.add(addButton, editButton, removeButton, addToBasketButton);
        add(buttonsLayout);
    }

    private void onEditProduct() {
        UI ui = UI.getCurrent();
        String productUrl = RouteConfiguration.forApplicationScope()
                .getUrl(EditProductView.class, selectedProduct.getId());
        ui.navigate(productUrl);
    }

    private void onRemoveProduct() {
        ConfirmDialog confirmDialog = new ConfirmDialog("Warning", "Are you sure?",
                "Yes", event -> removeSelectedProduct(), "No", event -> {});
        confirmDialog.open();
    }

    private void removeSelectedProduct() {
        productService.removeProduct(selectedProduct);
        refreshProducts();
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
        addToBasketButton.setVisible(product != null && product.getQuantity() > 0);
    }

    private void refreshProducts() {
        List<Product> products = productService.getAllProduct();
        productsGrid.setProducts(products);
    }

    private void onAddProductToBasket() {
        productService.decreaseQuantity(selectedProduct.getId(), DEFAULT_QUANTITY);
        order.add(selectedProduct.getId(), DEFAULT_QUANTITY);
        disposableEventBus.fireEvent(new ProductQuantityChangeEvent());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
       UI ui = attachEvent.getUI();
       disposableEventBus.on(ProductQuantityChangeEvent.class, event -> ui.access(this::refreshProducts));
    }

}
