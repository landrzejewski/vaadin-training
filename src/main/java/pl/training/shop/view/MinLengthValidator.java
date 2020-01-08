package pl.training.shop.view;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import lombok.RequiredArgsConstructor;

import static com.vaadin.flow.data.binder.ValidationResult.*;

@RequiredArgsConstructor
public class MinLengthValidator implements Validator<String> {

    private final int minLength;

    @Override
    public ValidationResult apply(String value, ValueContext valueContext) {
        return value.length() < minLength ? error("Value is too short") : ok();
    }

}
