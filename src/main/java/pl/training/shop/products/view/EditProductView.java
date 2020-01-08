package pl.training.shop.products.ui;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductsService;
import pl.training.shop.ui.CancelEvent;
import pl.training.shop.ui.SaveEvent;

@Route("edit-product")
public class EditProductView extends VerticalLayout implements HasUrlParameter<Long>, AfterNavigationObserver {
	
	private final ProductsService productsService;
	private Long productId;
	private ProductForm form;
	
	@Autowired
	public EditProductView(ProductsService productsService) {
		this.productsService = productsService;		
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

	@Override
	public void setParameter(BeforeEvent event, Long productId) {
		this.productId = productId;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		Optional<Product> product = productsService.getProduct(productId);
		if (product.isPresent()) {
			form = new ProductForm(product.get(), productsService.getAllProductCategories());
			initProductForm(); 
		} else {
			showProducts();
		}
	}

}
