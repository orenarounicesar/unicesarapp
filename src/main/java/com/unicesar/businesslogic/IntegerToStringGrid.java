
package com.unicesar.businesslogic;

import com.unicesar.utils.SeveralProcesses;
import com.vaadin.data.util.converter.Converter;
import java.util.Locale;

public class IntegerToStringGrid implements Converter<String, Integer> {
    
    private final String vTabla;
    private final String vCodigo;
    private final String vNombre;
    private final String vServidor;
    
    public IntegerToStringGrid(String vTabla, String vCodigo, String vNombre) {
        this.vTabla = vTabla;
        this.vCodigo = vCodigo;
        this.vNombre = vNombre;
        this.vServidor = null;
    }      

    public IntegerToStringGrid(String vTabla, String vCodigo, String vNombre, String servidor) {
        this.vTabla = vTabla;
        this.vCodigo = vCodigo;
        this.vNombre = vNombre;
        this.vServidor = servidor;
    }      

    @Override
    public Integer convertToModel(String value, Class<? extends Integer> targetType, Locale locale) throws ConversionException {
        throw new ConversionException("Not supported");
    }

    @Override
    public String convertToPresentation(Integer value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        if (value == null)
            return null;
        else
            return SeveralProcesses.retornarInformacion(vTabla, vCodigo, vNombre, value);
    }

    @Override
    public Class<Integer> getModelType() {
        return Integer.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
    
}
