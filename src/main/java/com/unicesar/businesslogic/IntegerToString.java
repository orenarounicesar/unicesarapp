
package com.unicesar.businesslogic;

import com.vaadin.data.util.converter.Converter;
import java.util.Locale;

public class IntegerToString implements Converter<Integer, String> {

    @Override
    public String convertToModel(Integer value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        if (value == null)
            return null;
        return value.toString();
    }

    @Override
    public Integer convertToPresentation(String value, Class<? extends Integer> targetType, Locale locale) throws ConversionException {
        if (value == null) 
            return null;
        return Integer.valueOf(value);
    }

    @Override
    public Class<String> getModelType() {
        return String.class;
    }

    @Override
    public Class<Integer> getPresentationType() {
        return Integer.class;
    }
    
}
