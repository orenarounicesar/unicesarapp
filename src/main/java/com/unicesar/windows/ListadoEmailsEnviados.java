
package com.unicesar.windows;

import com.unicesar.beans.Email;
import com.unicesar.components.TableWithFilterSplit;
import com.unicesar.utils.Enrutador;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Window;

/**
 *
 * @author orenaro
 */
public class ListadoEmailsEnviados extends Window {

    
    private final TableWithFilterSplit tblEmails;
    
    public ListadoEmailsEnviados() {
        super("Listado de Emails Enviandos");
        
        tblEmails = new TableWithFilterSplit("email", "Listado de Emails Enviados", true);
        tblEmails.setSizeFull();
        tblEmails.setStyleName("tablafilasdelgadascomponente");
        tblEmails.layoutContent.setSizeFull();
        tblEmails.panel.setSizeFull();
        tblEmails.panel.setStyleName("panelverde");
        tblEmails.panel.addStyleName("bordeverde");
        
        tblEmails.setContainerDataSource(new BeanItemContainer(Email.class, Enrutador.getEmails()));
        
        setContent(tblEmails.panel);
        
        setWidth("80%");
        setHeight("80%");
        setResizable(true);
        setClosable(true);
        center();
        setModal(true);
    }
    
}
