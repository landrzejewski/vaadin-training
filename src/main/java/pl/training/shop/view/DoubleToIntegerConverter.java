package pl.training.shop.view;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class DoubleToIntegerConverter implements Converter<Double, Integer> {

    @Override
    public Result<Integer> convertToModel(Double value, ValueContext valueContext) {
        return Result.ok(value.intValue());
    }

    @Override
    public Double convertToPresentation(Integer value, ValueContext valueContext) {
        return Double.valueOf(value);
    }

}
