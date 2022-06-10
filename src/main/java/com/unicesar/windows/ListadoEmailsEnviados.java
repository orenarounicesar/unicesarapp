
package com.unicesar.windows;

import com.unicesar.components.TableWithFilterSplit;
import com.vaadin.ui.Window;

/**
 *
 * @author orenaro
 */
public class ListadoEmailsEnviados extends Window {

    
    private TableWithFilterSplit tblEmails;
    
    public ListadoEmailsEnviados() {
        super("Listado de Emails Enviandos");
        
        tblEmails = new TableWithFilterSplit("email", "Listado de Emails Enviados", true);
        tblEmails.setSizeFull();
        tblEmails.setStyleName("tablafilasdelgadascomponente");
        tblEmails.layoutContent.setSizeFull();
        tblEmails.panel.setSizeFull();
        tblEmails.panel.setStyleName("panelverde");
        tblEmails.panel.addStyleName("bordeverde");
    }
    
}
