package pl.training.shop.products.ui;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductsService;
import pl.training.shop.ui.CancelEvent;
import pl.training.shop.ui.SaveEvent;

@Route("new-product")
public class AddProductView extends VerticalLayout {
	
	private final ProductsService productsService;
	private final ProductForm form;
	
	@Autowired
	public AddProductView(ProductsService productsService) {
		this.productsService = productsService;
		Product product = new Product();
		product.setAvailableSince(new Date());
		form = new ProductForm(product, productsService.getAllProductCategories());
		initProductForm();
	}
	
	private void initProductForm() {
		form.addCancelListener(this::onProductFormCancel);
		form.addSaveListener(this::onProductFormSave);
		form.setWidth("300px");
		add(form);
	}
	
	private void onProductFormCancel(CancelEvent event) {
		showProducts();
	}
	
	private void onProductFormSave(SaveEvent event) {
		Product product = form.getProduct();
		productsService.updateProduct(product);
		showProducts();
	}
	
	private void showProducts() {
		UI.getCurrent().navigate(ProductsView.class);
	}

}
