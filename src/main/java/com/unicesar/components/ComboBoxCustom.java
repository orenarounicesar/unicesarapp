
package com.unicesar.components;

import com.unicesar.utils.SeveralProcesses;
import com.vaadin.data.Container;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.Collection;

public class ComboBoxCustom extends ComboBox {

    public ComboBoxCustom(String width, Boolean required, String[] valores, Boolean enabled) {
        setUp(width, required, valores, null, enabled);
    }

    public ComboBoxCustom(String width, Boolean required, String cadenaSql, Boolean enabled) {
        setUp(width, required, null, cadenaSql, enabled);
    }

    public ComboBoxCustom(String caption, Collection<?> options, String width, Boolean required, Boolean enabled) {
        super(caption, options);
        setUp(width, required, null, null, enabled);
    }

    public ComboBoxCustom(String caption, Container dataSource, String width, Boolean required, Boolean enabled) {
        super(caption, dataSource);
        setUp(width, required, null, null, enabled);
    }

    public ComboBoxCustom(String caption, String width, Boolean required, String[] valores, Boolean enabled) {
        super(caption);
        setUp(width, required, valores, null, enabled);
    }
    
    public ComboBoxCustom(String caption, String width, Boolean required, String cadenaSql, Boolean enabled) {
        super(caption);
        setUp(width, required, null, cadenaSql, enabled);
    }
    
    public ComboBoxCustom(String caption, String width, Boolean required, String cadenaSql, Boolean enabled, boolean llaveTexto) {
        super(caption);
        if (llaveTexto)
            setUpText(width, required, null, cadenaSql, enabled);
        else
            setUp(width, required, null, cadenaSql, enabled);
    }
    
    public ComboBoxCustom(String caption, String width, Boolean required, Boolean enabled, ArrayList<String> valores) {
        super(caption);
        setUp(width, required, null, null, enabled);
        if (valores != null)
            for (int i = 0; i < valores.size(); i += 2) {
                this.addItem(valores.get(i));
                this.setItemCaption(valores.get(i), valores.get(i + 1));
            }
    }
    
    private void setUp (String width, Boolean required, String[] valores, String cadenaSql, Boolean enabled) {
        this.setFilteringMode(FilteringMode.CONTAINS);
        setWidth(width);
        setRequired(required);
        setEnabled(enabled);
//        setStyleName("delgado");
        if (valores != null)
            for (String valor: valores) {
                addItem(valor);
            }
        if (cadenaSql != null && !cadenaSql.isEmpty())
            SeveralProcesses.addItemsComboDefault(this, cadenaSql);
    }
    
    private void setUpText (String width, Boolean required, String[] valores, String cadenaSql, Boolean enabled) {
        this.setFilteringMode(FilteringMode.CONTAINS);
        setWidth(width);
        setRequired(required);
        setEnabled(enabled);
//        setStyleName("delgado");
        if (valores != null)
            for (String valor: valores) {
                addItem(valor);
            }
        if (cadenaSql != null && !cadenaSql.isEmpty())
            SeveralProcesses.addTextItemsComboDefault(this, cadenaSql);
    }
    
    public void setAltoMinimo() {
        this.setStyleName(ValoTheme.COMBOBOX_TINY, true);
    }
}
