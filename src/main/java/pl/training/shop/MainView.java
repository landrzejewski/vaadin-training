package pl.training.shop;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import pl.training.shop.products.view.ProductsView;

@Route
public class MainView extends VerticalLayout {

    private final HorizontalLayout menuLayout = new HorizontalLayout();

    public MainView() {
        initButtons();
        add(menuLayout);
    }

    private void initButtons() {
        Button productsButton = new Button(getTranslation("products"));
        productsButton.addClickListener(event -> UI.getCurrent().navigate(ProductsView.class));
        menuLayout.add(productsButton);
    }

}
