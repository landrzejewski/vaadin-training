package pl.training.shop.orders.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import pl.training.shop.MainView;
import pl.training.shop.orders.model.Order;
import pl.training.shop.orders.model.OrderEntry;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductService;


import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Route("order")
public class OrderView extends VerticalLayout {

    private OrderGrid orderGrid = new OrderGrid();
    private Label summary = new Label();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();
    private Button resignButton = new Button();
    private Button acceptButton = new Button();

    private Order order;
    private ProductService productsService;

    public OrderView(Order order, ProductService productsService) {
        this.order = order;
        this.productsService = productsService;
        add(orderGrid, summary);
        initButtons();
        initData();
    }

    private void initButtons() {
        resignButton.setText("Resign");
        resignButton.addClickListener(event -> resign());
        acceptButton.setText("Accept");
        acceptButton.addClickListener(event -> acceptOrder());
        buttonsLayout.add(resignButton, acceptButton);
        add(buttonsLayout);
    }

    private void resign() {
        order.getEntries().forEach(productsService::increaseQuantity);
        order.clear();
        UI.getCurrent().navigate(MainView.class);
    }

    private void acceptOrder() {
        order.clear();
        UI.getCurrent().navigate(MainView.class);
    }

    private void initData() {
        List<OrderEntry> orderEntries = order.getEntries().entrySet().stream()
                .map(this::toOrderEntry)
                .collect(toList());
        long totalValue = orderEntries.stream()
                .mapToLong(OrderEntry::getValue)
                .sum();
        orderGrid.setItems(orderEntries);
        summary.setText("Total value: " + totalValue);
    }

    private OrderEntry toOrderEntry(Map.Entry<Long, Integer> entry) {
        Product product = productsService.getProduct(entry.getKey()).get();
        return new OrderEntry(product, entry.getValue());
    }

}
