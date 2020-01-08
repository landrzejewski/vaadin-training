package pl.training.shop.products.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import lombok.extern.java.Log;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductCategory;
import pl.training.shop.view.CancelEvent;
import pl.training.shop.view.DoubleToIntegerConverter;
import pl.training.shop.view.MinLengthValidator;
import pl.training.shop.view.SaveEvent;

import java.util.List;

@Log
public class ProductForm extends FormLayout {

    private final TextField nameField = new TextField();
    private final TextArea descriptionField = new TextArea();
    private final TextField priceField = new TextField();
    private final DatePicker availableSinceField = new DatePicker();
    private final NumberField quantityField = new NumberField();
    private final ComboBox<ProductCategory> categoryField = new ComboBox<>();
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private final Button cancelButton = new Button();
    private final Button saveButton = new Button();

    private final Binder<Product> productBinder = new Binder<>(Product.class);
    private final BeanValidationBinder<Product> validationBinder = new BeanValidationBinder<>(Product.class);
    private final List<ProductCategory> productCategories;
    @Getter
    private Product product;

    public ProductForm(Product product, List<ProductCategory> productCategories) {
        this.product  = product;
        this.productCategories = productCategories;
        initFields();
        initButtons();
        bindProduct();
        productBinder.readBean(product);
        validationBinder.readBean(product);
    }

    private void bindProduct() {
        productBinder.forField(nameField)
                .asRequired("Name is required")
                .bind(Product::getName, Product::setName);
        productBinder.forField(descriptionField)
                .asRequired("Description is required")
                .withValidator(new MinLengthValidator(3))
                .bind(Product::getDescription, Product::setDescription);
        productBinder.forField(priceField)
                .asRequired("Price is required")
                .withConverter(new StringToLongConverter("Invalid price"))
                .withValidator(price -> price > 0, "Price is too low")
                .bind(Product::getPrice, Product::setPrice);
        productBinder.forField(availableSinceField)
                .asRequired("Price is required")
                .withConverter(new LocalDateToDateConverter())
                .bind(Product::getAvailableSince, Product::setAvailableSince);
        productBinder.forField(quantityField)
                .asRequired("Quantity is required")
                .withConverter(new DoubleToIntegerConverter())
                .bind(Product::getQuantity, Product::setQuantity);
        productBinder.forField(categoryField)
                .asRequired("Category is required")
                .bind(Product::getCategory, Product::setCategory);
        validationBinder.forField(nameField)
                .bind("name");
    }

    private void initFields() {
        nameField.setLabel("Name");
        descriptionField.setLabel("Description");
        priceField.setLabel("Price");
        availableSinceField.setLabel("Available since");
        quantityField.setLabel("Quantity");
        categoryField.setLabel("Category");
        categoryField.setItemLabelGenerator(ProductCategory::getName);
        categoryField.setItems(productCategories);
        add(nameField, descriptionField, priceField, availableSinceField, quantityField, categoryField);
    }

    private void initButtons() {
        cancelButton.setText("Cancel");
        cancelButton.addClickListener(this::onCancel);
        saveButton.setText("Save");
        saveButton.addClickListener(this::onSave);
        buttonsLayout.add(cancelButton, saveButton);
        add(buttonsLayout);
    }

	private void onCancel(ClickEvent<Button> event) {
        fireEvent(new CancelEvent(this));
	}
	
	private void onSave(ClickEvent<Button> event) {
        validationBinder.validate();
        log.info("Status: " + validationBinder.isValid());
        try {
            validationBinder.writeBean(product);
        } catch (ValidationException e) {
            log.warning(e.getMessage());
            return;
        }

        productBinder.writeBeanIfValid(product);
        if (productBinder.isValid()) {
            fireEvent(new SaveEvent(this));
        }
	}
	
	public Registration addCancelListener(ComponentEventListener<CancelEvent> listener) {
		return addListener(CancelEvent.class, listener);
	}
	
	public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
		return addListener(SaveEvent.class, listener);
	}
    
}
