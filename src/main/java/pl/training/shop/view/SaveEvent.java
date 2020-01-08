package pl.training.shop.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.formlayout.FormLayout;

public class SaveEvent extends ComponentEvent<FormLayout> {
	
	public SaveEvent(FormLayout source) {
		super(source, false);
	}

}
