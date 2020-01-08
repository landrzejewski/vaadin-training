package pl.training.shop.products.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductService;
import pl.training.shop.view.CancelEvent;
import pl.training.shop.view.SaveEvent;

@Route("edit-product")
public class EditProductView extends VerticalLayout {
	
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
		//productService.updateProduct(product);
		showProducts();
	}
	
	private void showProducts() {
	}

}
