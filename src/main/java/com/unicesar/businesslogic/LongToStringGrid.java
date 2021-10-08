
package com.unicesar.businesslogic;

import com.unicesar.utils.SeveralProcesses;
import com.vaadin.data.util.converter.Converter;
import java.util.Locale;


public class LongToStringGrid implements Converter<String, Long> {
    
    private String vTabla;
    private String vCodigo;
    private String vNombre;
    
    public LongToStringGrid(String vTabla, String vCodigo, String vNombre) {
        this.vTabla = vTabla;
        this.vCodigo = vCodigo;
        this.vNombre = vNombre;
    } 
    
    @Override
    public Long convertToModel(String value, Class<? extends Long> targetType, Locale locale) throws ConversionException {
        throw new ConversionException("Not supported");
    }

    @Override
    public String convertToPresentation(Long value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        if (value == null)
            return null;
        return SeveralProcesses.retornarInformacion(vTabla, vCodigo, vNombre, value.intValue());
    }

    @Override
    public Class<Long> getModelType() {
        return Long.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
    
}
