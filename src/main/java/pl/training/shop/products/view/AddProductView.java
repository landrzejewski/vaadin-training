package pl.training.shop.products.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductService;
import pl.training.shop.view.CancelEvent;
import pl.training.shop.view.SaveEvent;

import java.util.Date;

@Route("add-product")
public class AddProductView extends VerticalLayout {
	
	private final ProductService productService;
	private final ProductForm form;
	
	@Autowired
	public AddProductView(ProductService productsService) {
		this.productService = productsService;
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
		productService.saveProduct(product);
		showProducts();
	}
	
	private void showProducts() {
		UI.getCurrent().navigate(ProductsView.class);
	}

}
