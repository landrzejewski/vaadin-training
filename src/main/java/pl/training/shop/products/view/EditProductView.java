package pl.training.shop.products.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductService;
import pl.training.shop.view.CancelEvent;
import pl.training.shop.view.SaveEvent;

import java.util.Optional;

@Route("edit-product")
public class EditProductView extends VerticalLayout implements HasUrlParameter<Long>, AfterNavigationObserver {
	
	private final ProductService productService;
	private Long productId;
	private ProductForm form;
	
	@Autowired
	public EditProductView(ProductService productsService) {
		this.productService = productsService;
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

	@Override
	public void setParameter(BeforeEvent beforeEvent, Long productId) {
		this.productId = productId;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
		Optional<Product> product = productService.getProduct(productId);
		if (product.isPresent()) {
			form = new ProductForm(product.get(), productService.getAllProductCategories());
			initProductForm();
		} else {
			showProducts();
		}
	}

}
