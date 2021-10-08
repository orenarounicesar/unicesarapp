
package com.unicesar.businesslogic;

import com.vaadin.data.util.converter.Converter;
import java.util.Locale;


public class IntegerToLong implements Converter<Integer, Long> {

    @Override
    public Long convertToModel(Integer value, Class<? extends Long> targetType, Locale locale) throws ConversionException {
        if (value == null)
            return null;
        return value.longValue();
    }

    @Override
    public Integer convertToPresentation(Long value, Class<? extends Integer> targetType, Locale locale) throws ConversionException {
        if (value == null) 
            return null;
        return Integer.valueOf(value.toString());
    }

    @Override
    public Class<Long> getModelType() {
        return Long.class;
    }

    @Override
    public Class<Integer> getPresentationType() {
        return Integer.class;
    }
    
}
