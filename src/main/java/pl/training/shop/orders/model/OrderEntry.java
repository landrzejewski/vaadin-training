package pl.training.shop.orders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.training.shop.products.model.Product;

@AllArgsConstructor
@Data
public class OrderEntry {

    private final Product product;
    private int quantity;

    public long getValue() {
        return product.getPrice() * quantity;
    }

}
