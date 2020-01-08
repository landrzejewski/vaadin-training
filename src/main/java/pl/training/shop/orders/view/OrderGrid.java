package pl.training.shop.orders.view;

import com.vaadin.flow.component.grid.Grid;
import pl.training.shop.orders.model.OrderEntry;

public class OrderGrid extends Grid<OrderEntry> {

    public OrderGrid() {
        initGrid();
    }

    private void initGrid() {
        addColumn(orderEntry -> orderEntry.getProduct().getName()).setHeader("Name");
        addColumn(orderEntry -> orderEntry.getProduct().getPrice()).setHeader("Product");
        addColumn(OrderEntry::getQuantity).setHeader("Quantity");
        addColumn(OrderEntry::getValue).setHeader("Value");
    }

}
