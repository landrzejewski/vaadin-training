package pl.training.shop.orders.model;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@VaadinSessionScope
@Component
@Log
public class Order {

    private Map<Long, Integer> entries = new HashMap<>();

    public void add(Long productId, int quantity) {
        int currentQuantity = entries.getOrDefault(productId, 0);
        entries.put(productId, quantity + currentQuantity);
    }

    public void clear() {
        entries.clear();
    }

    @PostConstruct
    public void init() {
        log.info("Order initialized");
    }

    @PreDestroy
    public void destroy() {
        log.info("Order destroyed");
    }

}
