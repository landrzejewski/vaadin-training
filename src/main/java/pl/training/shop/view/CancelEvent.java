package pl.training.shop.ui;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.formlayout.FormLayout;

public class CancelEvent extends ComponentEvent<FormLayout> {
	
	public CancelEvent(FormLayout source) {
		super(source, false);
	}

}
