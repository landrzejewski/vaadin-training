package pl.training.shop;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.*;
import lombok.extern.java.Log;
import pl.training.shop.orders.view.OrderView;
import pl.training.shop.products.model.ProductService;
import pl.training.shop.products.view.ProductsView;
import pl.training.shop.view.AppHello;

@Push
@Route
@Log
public class MainView extends VerticalLayout implements BeforeLeaveObserver, AfterNavigationObserver {

    private final HorizontalLayout menuLayout = new HorizontalLayout();
    private final Label productsCount = new Label();
    private final ProductService productService;
    private Thread backgroundThread;

    public MainView(ProductService productService) {
        this.productService = productService;
        initButtons();
        add(menuLayout);
        add(productsCount);
        add(new AppHello());
    }

    private void initButtons() {
        Button productsButton = new Button(getTranslation("products"));
        productsButton.addClickListener(event -> UI.getCurrent().navigate(ProductsView.class));
        Button orderButton = new Button("Order");
        orderButton.addClickListener(event -> UI.getCurrent().navigate(OrderView.class));
        menuLayout.add(productsButton, orderButton);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        backgroundThread = new Thread(() -> {
           while (!backgroundThread.isInterrupted()) {
               attachEvent.getUI().access(this::updateProductsCounter);
               try {
                   Thread.sleep(5_000);
               } catch (InterruptedException e) {
                   log.info("Unregistering listeners");
                   break;
               }
           }
        });
        backgroundThread.start();
    }

    private void updateProductsCounter() {
        String productsInfo = String.format("Products count: %d", productService.getProductsCount());
        productsCount.setText(productsInfo);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        updateProductsCounter();
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
        backgroundThread.interrupt();
    }

}
