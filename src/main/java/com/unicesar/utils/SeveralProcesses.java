
package com.unicesar.utils;

import com.unicesar.beans.DatosFilasTablas;
import com.unicesar.businesslogic.GestionDB;
import com.unicesar.businesslogic.GestionDBException;
import com.unicesar.businesslogic.Notifications;
import com.unicesar.components.ComboBoxCustom;
import com.unicesar.components.ComponentClass;
import com.unicesar.components.LabelClick;
import com.unicesar.components.NumberFieldCustom;
import com.unicesar.components.TableWithFilter;
import com.unicesar.components.TextAreaCustom;
import com.unicesar.components.TextFieldCustom;
import com.unicesar.components.TextFieldMask;
import com.unicesar.components.TwinColSelectCustom;
import com.vaadin.data.Item;
import com.vaadin.server.StreamResource;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestionProvider;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteTextField;
import eu.maxschuster.vaadin.autocompletetextfield.provider.CollectionSuggestionProvider;
import eu.maxschuster.vaadin.autocompletetextfield.provider.MatchMode;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import org.apache.commons.io.IOUtils;
import org.vaadin.teemu.switchui.Switch;
import org.vaadin.ui.NumberField;

public class SeveralProcesses {
    
    public static boolean validateComponent(Component next) {
        for (Class componente : ComponentClass.componentes) {
            if (next.getClass() == componente) {
                return true;
            }
        }
        return false;
    }
    
    public static String retornarInformacion(String vTabla, String vCodigo, String vNombre, Integer value) {
        String retorno = "";
        GestionDB objConnect = null;
        String cadenaSql = "SELECT " + vNombre + " AS nombre FROM " + vTabla + " WHERE " + vCodigo + " = " + value;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            rs.next();
            retorno = rs.getString("nombre");
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex);
            Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);            
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static void confirmarSentencia (String mensaje)throws GestionDBException{
        if (!mensaje.substring(0, 4).equals("true")) {
            throw new GestionDBException(mensaje);            
        }
    }
    
    public static ArrayList getItemsSelected(String cadenaSql){
        ArrayList<Integer> array = new ArrayList<>();
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rsItemsSelected = objConnect.consultar(cadenaSql);
            while(rsItemsSelected.next()){
                array.add(rsItemsSelected.getInt(1));
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, getSessionUser(), ex);
            Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);            
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return array;
    }
    
    public static void setGeneralComponentStatus(ComponentContainer componentContainer, Boolean componentStatus) {
        ArrayList<ComponentContainer> listComponentContainer = new ArrayList<>();
        listComponentContainer.add(componentContainer);
        
        while(!listComponentContainer.isEmpty()){
            Iterator<Component> componentIterator = listComponentContainer.get(0).iterator();

            while(componentIterator.hasNext()){
                Component next = componentIterator.next();
                
                if(validateComponent(next)){
                    if (!(next.getClass() == Grid.class) && !(next.getClass() == Table.class))                         
                        next.setEnabled(componentStatus);                    
                } else {
//                    if (!next.getClass().getSuperclass().equals(ToolsBarr.class)) {
                        if (next.getClass().equals(Panel.class)) {
                            Component content = ((Panel) next).getContent();
                            if(validateComponent(content)) {
                                next.setEnabled(componentStatus);
                            } else {
                                listComponentContainer.add((ComponentContainer) content);
                            }
                        } else {
                            listComponentContainer.add((ComponentContainer) next);
                        }
//                    }
                }
            }            
            listComponentContainer.remove(0);
        }
    }
    
    public static ArrayList<Integer> getContainerItemsSelected(ComponentContainer componentContainer, String key, String propertyCheckBox) {
        ArrayList<Integer> keys = new ArrayList<>();
        ArrayList<ComponentContainer> listComponentContainer = new ArrayList<>();
        listComponentContainer.add(componentContainer);
        
        while (!listComponentContainer.isEmpty()) {
            Iterator<Component> componentIterator = listComponentContainer.get(0).iterator();

            while (componentIterator.hasNext()) {
                Component next = componentIterator.next();
                
                if (validateComponent(next)) {
                    if (next.getClass().equals(Table.class) || next.getClass().equals(TableWithFilter.class)) {
                        Table tabla = (Table) next;
                        getTableItemSelected(tabla, key, propertyCheckBox, keys);
                    }                        
                } else {
//                    if (!next.getClass().getSuperclass().equals(ToolsBarr.class)) {
                        if (next.getClass().equals(Panel.class)) {
                            Component content = ((Panel) next).getContent();
                            if (validateComponent(content)) {
                                if (content.getClass().equals(Table.class) || next.getClass().equals(TableWithFilter.class)) {
                                    Table tabla = (Table) content;
                                    getTableItemSelected(tabla, key, propertyCheckBox, keys);
                                }
                            } else {
                                listComponentContainer.add((ComponentContainer) content);
                            }
                        } else {
                            listComponentContainer.add((ComponentContainer) next);
                        }
//                    }
                }
            }            
            listComponentContainer.remove(0);
        }
        return keys;
    }
    
    private static void getTableItemSelected(Table tabla, String key, String propertyCheckBox, ArrayList<Integer> keys) {
        for (Iterator iterator = tabla.getItemIds().iterator(); iterator.hasNext();) {
            Integer iid = (Integer) iterator.next();
            Item item = tabla.getItem(iid);
            CheckBox checkBox = (CheckBox) item.getItemProperty(propertyCheckBox).getValue();
            if (checkBox.getValue()) {
                keys.add((Integer) item.getItemProperty(key).getValue());
            }
        }
    }
    
    public ArrayList<ItemComentario> getContainerItemsSelected(ComponentContainer componentContainer, String keyCodigo, 
            String keySi, String keyComentario) {
        ArrayList<ItemComentario> itemsSelected = new ArrayList<>();
        ArrayList<ComponentContainer> listComponentContainer = new ArrayList<>();
        listComponentContainer.add(componentContainer);
        
        while (!listComponentContainer.isEmpty()) {
            Iterator<Component> componentIterator = listComponentContainer.get(0).iterator();

            while (componentIterator.hasNext()) {
                Component next = componentIterator.next();
                
                if (validateComponent(next)) {
                    if (next.getClass().equals(Table.class)) {
                        Table tabla = (Table) next;
                        getTableItemSelected(tabla, keyCodigo, keySi, keyComentario, itemsSelected);
                    }                        
                } else {
                    if (next.getClass().equals(Panel.class)) {
                        Component content = ((Panel) next).getContent();
                        if (validateComponent(content)) {
                            if (content.getClass().equals(Table.class)) {
                                Table tabla = (Table) content;
                                getTableItemSelected(tabla, keyCodigo, keySi, keyComentario, itemsSelected);
                            }
                        } else {
                            listComponentContainer.add((ComponentContainer) content);
                        }
                    } else {
                        listComponentContainer.add((ComponentContainer) next);
                    }
                }
            }            
            listComponentContainer.remove(0);
        }
        return itemsSelected;
    }

    private void getTableItemSelected(Table tabla, String keyCodigo, String keySi, 
            String keyComentario, ArrayList<ItemComentario> itemsSelected) {
        for (Iterator iterator = tabla.getItemIds().iterator(); iterator.hasNext();){
            Integer iid = (Integer) iterator.next();
            Item item = tabla.getItem(iid);
            CheckBox checkBox = (CheckBox) item.getItemProperty(keySi).getValue();
            if (checkBox.getValue()) {
                ItemComentario itemComentario = new ItemComentario();
                itemComentario.setCodigo((Integer) item.getItemProperty(keyCodigo).getValue());
//                System.out.println(item.getItemProperty(keyComentario).getValue().getClass());
                String comentario;
                if (item.getItemProperty(keyComentario).getValue().getClass().equals(TextFieldCustom.class))
                    comentario = getValorComponente((TextFieldCustom) item.getItemProperty(keyComentario).getValue(), true);
                else 
                    comentario = getValorComponente((TextArea) item.getItemProperty(keyComentario).getValue(), true);
                
                itemComentario.setComentario(comentario);
                itemsSelected.add(itemComentario);
            }
        }
    }
    
    public static ArrayList<TextArea> getContainerTextArea(ComponentContainer componentContainer) {
        ArrayList<TextArea> values = new ArrayList<>();
        ArrayList<ComponentContainer> listComponentContainer = new ArrayList<>();
        listComponentContainer.add(componentContainer);
        
        while (!listComponentContainer.isEmpty()) {
            Iterator<Component> componentIterator = listComponentContainer.get(0).iterator();

            while (componentIterator.hasNext()) {
                Component next = componentIterator.next();
                
                if (validateComponent(next)) {
                    if (next.getClass().equals(TextArea.class)) {
                        values.add((TextArea) next);
                    }                        
                } else {
//                    if (!next.getClass().getSuperclass().equals(ToolsBarr.class)) {
                        if (next.getClass().equals(Panel.class)) {
                            Component content = ((Panel) next).getContent();
                            if (validateComponent(content)) {
                                if (content.getClass().equals(TextArea.class)) {
                                    values.add((TextArea) content);
                                }
                            } else {
                                listComponentContainer.add((ComponentContainer) content);
                            }
                        } else {
                            listComponentContainer.add((ComponentContainer) next);
                        }
//                    }
                }
            }            
            listComponentContainer.remove(0);
        }
        return values;
    }
    
    public static String getValorComponente(Component component, boolean comillas) {
        String retorno = null;
        if (component.getClass().equals(ComboBoxCustom.class) || component.getClass().equals(ComboBox.class)) {
            ComboBox comboBox = (ComboBox) component;
            if (comboBox.getValue() != null)
                if (comillas)
                    retorno = "'" + comboBox.getValue() + "'";
                else
                    retorno = comboBox.getValue().toString();                    
        }
            
        if (component.getClass().equals(PopupDateField.class)) {
            PopupDateField popupDateField = (PopupDateField) component;
            if (popupDateField.getValue() != null)
                if (comillas)
                    retorno = "'" + new SimpleDateFormat("yyyy-MM-dd").format(popupDateField.getValue()) + "'";
                else
                    retorno = new SimpleDateFormat("yyyy-MM-dd").format(popupDateField.getValue());
        }
            
        if (component.getClass().equals(TextField.class) || component.getClass().equals(TextFieldCustom.class) || 
                component.getClass().equals(NumberField.class) || component.getClass().equals(NumberFieldCustom.class) || 
                component.getClass().equals(AutocompleteTextField.class)) {
            TextField textField = (TextField) component;
            if (textField.getValue() != null && !textField.getValue().trim().isEmpty())
                if (textField.getCaption() != null && textField.getCaption().equalsIgnoreCase("Email")) {
                    if (comillas)
                        retorno = "'" + getEscapedValue(textField.getValue().trim().toLowerCase()) + "'";
                    else
                        retorno = getEscapedValue(textField.getValue().trim().toLowerCase());
                } else if (textField.getCaption() != null && (textField.getCaption().equalsIgnoreCase("Primer Nombre") || 
                        textField.getCaption().equalsIgnoreCase("Segundo Nombre") || 
                        textField.getCaption().equalsIgnoreCase("Primer Apellido") || 
                        textField.getCaption().equalsIgnoreCase("Segundo Apellido"))) {
                    if (comillas)
                        retorno = "'" + getEscapedValue(textField.getValue().trim().toUpperCase()) + "'";
                    else
                        retorno = getEscapedValue(textField.getValue().trim().toUpperCase());
                } else {
                    if (comillas) {
                        retorno = "'" + getEscapedValue(textField.getValue().trim()) + "'";
                    } else {
                        retorno = getEscapedValue(textField.getValue().trim());
                    }
                }
        }
            
        if (component.getClass().equals(TextArea.class) || component.getClass().equals(TextAreaCustom.class)) {
            TextArea textArea = (TextArea) component;
            if (textArea.getValue() != null && !textArea.getValue().trim().isEmpty())
                if (comillas)
                    retorno = "'" + getEscapedValue(textArea.getValue()) + "'";
                else
                    retorno = getEscapedValue(textArea.getValue());                    
        }
            
        return retorno;
    }

    public class ItemComentario {
        private int codigo;
        private String comentario;

        public int getCodigo() {
            return codigo;
        }

        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }        
    }
    
    public static void setHeightTable(Table table, int cantidadMaximaFilas) {
        table.setHeightUndefined();
        if (table.size() > cantidadMaximaFilas) {
            table.setPageLength(cantidadMaximaFilas);
        } else {
            if (table.size() == 0)
                table.setPageLength(1);
            else
                table.setPageLength(table.size());
        }
    }
    
    public static void setHeightTable(Grid grid, int cantidadMaximaFilas) {
        grid.setHeightUndefined();
        if (grid.getContainerDataSource().size() > cantidadMaximaFilas) {
            grid.setHeightByRows(cantidadMaximaFilas);
        } else {
            if (grid.getContainerDataSource().size() == 0)
                grid.setHeightByRows(1);
            else
                grid.setHeightByRows(grid.getContainerDataSource().size());
        }
    }
    
    public static String getPrivilegio() {
        String retorno = null;
        String cadenaSql = "SELECT privilegio FROM app_users WHERE login = '" + UI.getCurrent().getSession().getAttribute(VariablesSesion.LOGIN) +"'";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if(rs.next()){
                retorno = rs.getString(1);
            }
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql  + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static String getCodigoProfesional() {
        String retorno = "0";
        String cadenaSql = "SELECT codigo_profesional FROM app_users_profesionales WHERE login = '" + getSessionUser() +"'";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if(rs.next()){
                retorno = rs.getString(1);
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static ArrayList<String> getCodigoProveedores() {
        ArrayList<String> retorno = new ArrayList<>();
        String cadenaSql = "SELECT codigo_proveedor "
                + "FROM profesionales_proveedores a "
                + "INNER JOIN app_users_profesionales b ON b.codigo_profesional = a.codigo_Profesional AND b.login = '" + getSessionUser() +"'";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if(rs.next()){
                retorno.add(rs.getString(1));
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static ArrayList<String> getCodigoProveedoresTurno() {
        ArrayList<String> retorno = new ArrayList<>();
        String cadenaSql = "SELECT a.codigo_proveedor "
                + "FROM profesionales_proveedores a "
                + "INNER JOIN app_users_profesionales b ON b.codigo_profesional = a.codigo_Profesional AND b.login = '" + getSessionUser() +"' "
                + "INNER JOIN proveedores c ON c.codigo_proveedor = a.codigo_proveedor AND c.utiliza_turnos = 1 "
                + "WHERE a.activo = 1";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if(rs.next()){
                retorno.add(rs.getString(1));
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static String getNombreProfesional(Object codigoProfesional) {
        String retorno = "";
        String cadenaSql = "SELECT CONCAT_WS(' ',nombre1_profesional,nombre2_profesional,apellido1_profesional,apellido2_profesional) AS nombre_profesional "
                + "FROM profesionales WHERE codigo_profesional = " + codigoProfesional;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if(rs.next()){
                retorno = rs.getString(1);
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static String getLoginDeCodigoSedeCliente(String codigoSede) {
        String retorno = null;
        String cadenaSql = "SELECT GROUP_CONCAT(login SEPARATOR ',') AS resultado "
                + "FROM app_users_empresas_clientes_sedes "
                + "WHERE codigo_empresa_cliente_sede = " + codigoSede;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if(rs.next()){
                retorno = "('" + rs.getString(1).replace(",", "','") + "')";
            }
        } catch (SQLException | NullPointerException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static ArrayList<String> getLoginDeProfesional(ArrayList<String> retorno, Object codigoProfesional) {
        if (retorno == null)
            retorno = new ArrayList<>();
        String cadenaSql = "SELECT login "
                + "FROM app_users_profesionales "
                + "WHERE codigo_profesional = " + codigoProfesional;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            while(rs.next()) {
                if (!retorno.contains(rs.getString(1)))
                    retorno.add(rs.getString(1));
            }
        } catch (SQLException | NullPointerException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static ArrayList<String> getEmailLogin(Object login) {
        ArrayList<String> retorno = new ArrayList<>();
        String cadenaSql = "SELECT a.email "
                    + "FROM app_users_mail_notificaciones a "
                    + "WHERE a.login = " + getEscapedStringValue(login);
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            while (rs.next()) {
                if ( !retorno.contains(rs.getString(1)) )
                    retorno.add( rs.getString(1) );
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando conexión - " + SeveralProcesses.getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static AutocompleteSuggestionProvider getSuggestionProvider(String cadenaSql) {
        Collection<String> valores = new ArrayList<>();
        AutocompleteSuggestionProvider suggestionProvider = null;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            while (rs.next()) {
                valores.add(rs.getString(1));
            }
            suggestionProvider = new CollectionSuggestionProvider(valores, MatchMode.CONTAINS, true);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return suggestionProvider;
    }
    
    public static String generarNumeroOrdenacion(String cadenaSql) {
        String retorno = "1";
        GestionDB objConnnect = null;
        try {
            objConnnect = new GestionDB();
            ResultSet rs = objConnnect.consultar(cadenaSql);
            if (rs.next() && rs.getString("orden") != null) {
                retorno = rs.getString("orden");
            }
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex);
        } finally {
            try {
                if (objConnnect != null)
                    objConnnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return retorno;
    }
    
    public static void resaltarFilasTabla(Table table, String operador, ArrayList<DatosFilasTablas> datos, String propertyNotHighlighted) {
        table.setCellStyleGenerator(new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table source, Object itemId, Object propertyId) {
                Item item = table.getItem(itemId);
                if (propertyId == null) {
                    // Styling for row
                    if (operador.equals("=")) {
                        for (DatosFilasTablas dato : datos) {
                            if (item.getItemProperty(dato.getProperty()).getValue().equals(dato.getValor()))
                                return dato.getStyleName();
                        }
                    } else if (operador.equals("!=")) {
                        for (DatosFilasTablas dato : datos) {
                            if (item.getItemProperty(dato.getProperty()).getValue() != null && !item.getItemProperty(dato.getProperty()).getValue().equals(dato.getValor()))
                                return dato.getStyleName();
                        }
                    }
                    if (propertyNotHighlighted == null)
                        return "tablefilasinresaltar";
                    else
                        return null;
                } else {
                    // styling for column propertyId
                    if (propertyId == propertyNotHighlighted) {
                        if (operador.equals("=")) {
                            for (DatosFilasTablas dato : datos) {
                                if (item.getItemProperty(dato.getProperty()).getValue().equals(dato.getValor()))
                                    return dato.getStyleName();
                            }
                        } else if (operador.equals("!=")) {
                            for (DatosFilasTablas dato : datos) {
                                if (item.getItemProperty(dato.getProperty()).getValue() != null 
                                        && !item.getItemProperty(dato.getProperty()).getValue().equals(dato.getValor()))
                                    return dato.getStyleName();
                            }
                        } 
                        if (propertyNotHighlighted == null)
                            return "tablefilasinresaltar";
                        else
                            return "celdasinrealtar";
                    } else {
                        return null;
                    }
                }
            }
        });
    }
    
    public static boolean getReadPrivilege(String name_menu) {
        boolean retorno = false;
        GestionDB objConnect = null;
        String cadenaSql = "SELECT mau.leer "
                + "FROM menu_app_users mau " 
                + "INNER JOIN menu m ON m.name_menu = mau.name_menu " 
                + "WHERE login = '" + getSessionUser() + "' AND " + 
                "mau.name_menu = '" + name_menu + "'";
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()){
                retorno = rs.getBoolean("leer");
            } 
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static boolean getPrintPrivilege(String name_menu) {
        boolean retorno = false;
        GestionDB objConnect = null;
        String cadenaSql = "SELECT mau.imprimir "
                + "FROM menu_app_users mau " 
                + "INNER JOIN menu m ON m.name_menu = mau.name_menu " 
                + "WHERE login = '" + getSessionUser() + "' AND " + 
                "mau.name_menu = '" + name_menu + "'";
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()){
                retorno = rs.getBoolean("imprimir");
            } 
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static boolean getInsertPrivilege(String name_menu) {
        boolean retorno = false;
        String cadenaSql = "SELECT mau.insertar "
                + "FROM menu_app_users mau " 
                + "INNER JOIN menu m ON m.name_menu = mau.name_menu " 
                + "WHERE login = '" + UI.getCurrent().getSession().getAttribute(VariablesSesion.LOGIN) + "' AND " + 
                "mau.name_menu = '" + name_menu + "'";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()){
                retorno = rs.getBoolean(1);
            } 
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static boolean getUpdatePrivilege(String name_menu) {
        boolean retorno = false;
        String cadenaSql = "SELECT mau.actualizar "
                + "FROM menu_app_users mau " 
                + "INNER JOIN menu m ON m.name_menu = mau.name_menu " 
                + "WHERE login = '" + UI.getCurrent().getSession().getAttribute(VariablesSesion.LOGIN) + "' AND " + 
                "mau.name_menu = '" + name_menu + "'";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()){
                retorno = rs.getBoolean(1);
            } 
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static boolean getDeletePrivilege(String name_menu) {
        boolean retorno = false;
        String cadenaSql = "SELECT mau.eliminar "
                + "FROM menu_app_users mau " 
                + "INNER JOIN menu m ON m.name_menu = mau.name_menu " 
                + "WHERE login = '" + UI.getCurrent().getSession().getAttribute(VariablesSesion.LOGIN) + "' AND " + 
                "mau.name_menu = '" + name_menu + "'";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()){
                retorno = rs.getBoolean(1);
            } 
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static boolean getLiderPrivilege(String name_menu) {
        boolean retorno = false;
        String cadenaSql = "SELECT mau.lider "
                + "FROM menu_app_users mau " 
                + "INNER JOIN menu m ON m.name_menu = mau.name_menu " 
                + "WHERE login = '" + UI.getCurrent().getSession().getAttribute(VariablesSesion.LOGIN) + "' AND " + 
                "mau.name_menu = '" + name_menu + "'";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()){
                retorno = rs.getBoolean(1);
            } 
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static boolean getFacturacionPrivilege(String name_menu) {
        boolean retorno = false;
        String cadenaSql = "SELECT mau.facturacion "
                + "FROM menu_app_users mau " 
                + "INNER JOIN menu m ON m.name_menu = mau.name_menu " 
                + "WHERE login = '" + UI.getCurrent().getSession().getAttribute(VariablesSesion.LOGIN) + "' AND " + 
                "mau.name_menu = '" + name_menu + "'";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()){
                retorno = rs.getBoolean(1);
            } 
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static boolean getValorPantallaPrivilege(String name_menu) {
        boolean retorno = false;
        String cadenaSql = "SELECT mau.valor_pantalla "
                + "FROM menu_app_users mau " 
                + "INNER JOIN menu m ON m.name_menu = mau.name_menu " 
                + "WHERE login = '" + UI.getCurrent().getSession().getAttribute(VariablesSesion.LOGIN) + "' AND " + 
                "mau.name_menu = '" + name_menu + "'";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()){
                retorno = rs.getBoolean(1);
            } 
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static boolean getValorReportePrivilege(String name_menu) {
        boolean retorno = false;
        String cadenaSql = "SELECT mau.valor_reporte "
                + "FROM menu_app_users mau " 
                + "INNER JOIN menu m ON m.name_menu = mau.name_menu " 
                + "WHERE login = '" + UI.getCurrent().getSession().getAttribute(VariablesSesion.LOGIN) + "' AND " + 
                "mau.name_menu = '" + name_menu + "'";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()){
                retorno = rs.getBoolean(1);
            } 
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static String getNumerosIngresos(String codigoUsuario, String codigoIngreso) {
        String retorno = "";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar("SELECT GROUP_CONCAT(a.codigo_ingreso SEPARATOR ',') AS ingresos FROM ingresos a "
                    + "INNER JOIN usuarios_datos b ON b.codigo_usuario = " + codigoUsuario + " AND b.codigo_usuarios_datos = a.codigo_usuarios_datos "
                    + "WHERE a.activo = 1 AND a.codigo_ingreso <= " + codigoIngreso + " "
                    + "GROUP BY b.codigo_usuario");
            if (rs.next()) {
                retorno = rs.getString(1);
            }
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
//    public static void gestionarSesionBitacora(String idSesion, boolean esCreacion) {
//        GestionDB objConnect = null;
//        try {
//            objConnect = new GestionDB();
//            if (esCreacion)
//                objConnect.insertarActualizarBorrar("INSERT INTO bitacora_sesiones ("
//                    + "id_sesion, "
//                    + "login, "
//                    + "fecha_creacion, "
//                    + "ip, "
//                    + "navegador"
//                    + ") VALUES ('"
//                    + idSesion + "', '"
//                    + UI.getCurrent().getSession().getAttribute(VariablesSesion.CURRENT_USER) + "', "
//                    + "now(), '"
//                    + UI.getCurrent().getPage().getWebBrowser().getAddress() + "', '"
//                    + getNavegador() + "'"
//                    + ") ON DUPLICATE KEY UPDATE "
//                    + "id_sesion = '" + idSesion + "'", false);
//            else
//                objConnect.insertarActualizarBorrar("INSERT INTO bitacora_sesiones ("
//                    + "id_sesion, "
//                    + "login, "
//                    + "fecha_creacion, "
//                    + "ip, "
//                    + "navegador"
//                    + ") VALUES ('"
//                    + idSesion + "', '"
//                    + UI.getCurrent().getSession().getAttribute(VariablesSesion.CURRENT_USER) + "', "
//                    + "now(), '"
//                    + UI.getCurrent().getPage().getWebBrowser().getAddress() + "', '"
//                    + getNavegador() + "'"
//                    + ") ON DUPLICATE KEY UPDATE "
//                    + "fecha_cierre = now()", false);
//        } catch (NamingException | SQLException ex) {
//            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, getSessionUser(), ex);
//        } finally {
//            try {
//                if (objConnect != null)
//                    objConnect.desconectar();
//            } catch (SQLException ex) {
//                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
//            }
//        }
//    }
    
    public static String getNavegador() {
        if (UI.getCurrent().getPage().getWebBrowser().isChrome())
            return "Google Chrome";
        else if (UI.getCurrent().getPage().getWebBrowser().isFirefox())
            return "Mozilla Firefox";
        else if (UI.getCurrent().getPage().getWebBrowser().isIE())
            return "Microsoft Intener Explorer";
        else if (UI.getCurrent().getPage().getWebBrowser().isEdge())
            return "Microsoft Edge";
        else if (UI.getCurrent().getPage().getWebBrowser().isSafari())
            return "Apple Safari";
        else if (UI.getCurrent().getPage().getWebBrowser().isOpera())
            return "Opera";
        
        return "";
    }
    
    public static StreamResource createResourceSoporte(String codigoArchivo) {
        StreamResource streamResource = null;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar("SELECT archivo, nombre_archivo FROM ingresos_registros_soportes WHERE codigo_ingreso_registro_soporte = " + codigoArchivo);
            if (rs.next()) {
                byte[] bytes = rs.getBytes("archivo");
                streamResource = new StreamResource(() -> new ByteArrayInputStream(bytes), rs.getString("nombre_archivo"));
            }
        } catch (NamingException | SQLException ex) {   
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return streamResource;
    }
    
    public static byte[] getBytesSoprote(String codigoArchivo) {
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar("SELECT archivo FROM ingresos_registros_soportes WHERE codigo_ingreso_registro_soporte = " + codigoArchivo);
            if (rs.next()) {
                return rs.getBytes("archivo");
            }
        } catch (NamingException | SQLException ex) {   
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return null;
    }
    
    public static Timestamp getFechaActualServidor() {
        Timestamp retorno = null;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar("SELECT NOW() AS fecha");
            if (rs.next()) {
                retorno = rs.getTimestamp("fecha");
            }
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static Date getFechaActualSinHoraServidor() {
        Date retorno = null;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar("SELECT CURDATE() AS fecha");
            if (rs.next()) {
                retorno = rs.getDate("fecha");
            }
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static boolean examenTieneResultado(String codigoIngresoRegistro) {
        boolean retorno = false;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar("SELECT IF(z.resultado IS NULL, 0, 1) AS resultado "
                + "FROM resultados z WHERE z.codigo_ingresos_registros = " + codigoIngresoRegistro + " LIMIT 1");
            if (rs.next()) {
                retorno = rs.getBoolean(1);
            }
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "SELECT IF(z.resultado IS NULL, 0, 1) AS resultado "
                + "FROM resultados z WHERE z.codigo_ingresos_registros = " + codigoIngresoRegistro + " LIMIT 1 - " + getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static String getSessionUser() {
        return UI.getCurrent().getSession().getAttribute(VariablesSesion.LOGIN).toString();
    }
    
    public static Object getObjectSessionUser() {
        return UI.getCurrent().getSession().getAttribute(VariablesSesion.LOGIN);
    }
    
    public static boolean validarMail(String mail) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return pattern.matcher(mail).find() == true;
    }
    
    public static File streamToFile (InputStream inputStream, String prefix) {
        try {
            File tempFile = File.createTempFile(prefix + "_" + getSessionUser(), ".tmp");
            tempFile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(inputStream, out);
            }
            return tempFile;
        }   catch (IOException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "stream2file - " + getSessionUser(), ex);
        }
        return null;
    }
    
    public static StreamResource inputStreamToStreamResource(InputStream inputStream, String fileName) {
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return new StreamResource(() -> new ByteArrayInputStream(bytes), fileName.replace(".", "_" + new SimpleDateFormat("kk_mm_ss").format(new Date()) + ".") );
        } catch (IOException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "inputStreamToStreamResource - " + getSessionUser(), ex);
            return null;
        }
    }
    
    public static String getCodigoProfesional(String codigoIngreso, String tipoFormato) {
        String retorno = "0";
        GestionDB objConnect = null;
        String cadenaSql = "SELECT codigo_profesional "
                + "FROM formatos_historia_clinica_guardados a "
                + "WHERE codigo_ingreso = " + codigoIngreso + " AND tipo_formato = '" + tipoFormato + "'";
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()) {
                retorno = rs.getString(1);
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static String getNombrePruebaComplementaria(String codigoReporte) {
        String retorno = "";
        String cadenaSql = "SELECT valor_parametro "
                + "FROM examenes_reportes_parametros WHERE codigo_examen_reporte = " + codigoReporte + " AND nombre_parametro = 'nombrePruebaComplementaria'";
        GestionDB objConnect = null;
        
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()) {
                retorno = rs.getString(1);
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static String getEscapedValue(Object value) {
        if (value == null)
            return null;
        else
            return value.toString().replace("'", "''").replace("\"", "\\" + "\"");
    }
    
    public static String getEscapedStringValue(Object value) {
        if (value == null)
            return null;
        else
            return "'" + getEscapedValue(value) + "'";
    }
    
    public static String getEscapedStringUpperValue(Object value) {
        if (value == null)
            return null;
        else
            return "'" + getEscapedValue(value).toUpperCase() + "'";
    }
    
    public static String getEscapedStringLowerValue(Object value) {
        if (value == null)
            return null;
        else
            return "'" + getEscapedValue(value).toLowerCase() + "'";
    }
    
//    public static String getUpperEscapedStringValue(Object value) {
//        if (value == null)
//            return null;
//        else
//            return "'" + getEscapedValue(value).toUpperCase() + "'";
//    }
    
    public static double getValorRedondeado(double valor, double decimales) {
        return Math.round(valor * decimales) / decimales;
    }
    
    public static ArrayList<String> getLoginDeComando(ArrayList<String> retorno, Object comando, Object codigoUnidadNegocio) {
        if (retorno == null)
            retorno = new ArrayList<>();
        String cadenaSql = "SELECT login "
                + "FROM comandos_app_users "
                + "WHERE comando = '" + comando + "' "
                + "AND (codigo_unidad_negocio = " + codigoUnidadNegocio + " OR codigo_unidad_negocio IS NULL)";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            while(rs.next()) {
                if (!retorno.contains(rs.getString(1)))
                    retorno.add(rs.getString(1));
            }
        } catch (SQLException | NullPointerException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static ArrayList<String> getLoginDeEmailSedeClienteCovid(ArrayList<String> retorno, Object codigoSede) {
        if (retorno == null)
            retorno = new ArrayList<>();
        String cadenaSql = "SELECT DISTINCT login "
                + "FROM app_users_empresas_clientes_sedes a "
                + "INNER JOIN empresas_clientes_sedes_mail_notificaciones b ON b.codigo_empresa_cliente_sede = a.codigo_empresa_cliente_sede "
                    + "AND b.codigo_empresa_cliente_sede = " + codigoSede;
        
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            while(rs.next()) {
                if (!retorno.contains(rs.getString(1)))
                    retorno.add(rs.getString(1));
            }
        } catch (SQLException | NullPointerException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static ArrayList<String> getLoginDeSedeCliente(ArrayList<String> retorno, Object codigoIngreso) {
        if (retorno == null)
            retorno = new ArrayList<>();
        String cadenaSql = "SELECT DISTINCT a.login "
                + "FROM app_users_empresas_clientes_sedes a "
                + "INNER JOIN ingresos b ON b.codigo_empresa_cliente_sede = a.codigo_empresa_cliente_sede "
                + " AND b.codigo_ingreso = " + codigoIngreso;
        
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            while(rs.next()) {
                if (!retorno.contains(rs.getString(1)))
                    retorno.add(rs.getString(1));
            }
        } catch (SQLException | NullPointerException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static boolean isMessgeSender() {
        boolean retorno = false;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar("SELECT envia_mensajes FROM app_users a WHERE a.login = " + getEscapedStringValue(getSessionUser()));
            if (rs.next()) {
                retorno = rs.getBoolean(1);
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión  - " + getSessionUser(), ex);
            }
        }
        
        
        return retorno;
    } 
    
    public static int getPosicionEspacioBlancoSiguiente(String mensaje, int posicion) {
        int retorno = posicion;
        if (posicion < mensaje.length()) {
            while (retorno < mensaje.length() && !mensaje.substring(retorno, retorno + 1).trim().isEmpty()) {
                retorno += 1;
            }
        } else {
            retorno = mensaje.length() + 1;
        }
        return retorno;
    }
    
    public static HashMap getClientInformation() {
        HashMap retorno = new HashMap();
        WebBrowser webBrowser = UI.getCurrent().getPage().getWebBrowser();
        if (webBrowser.isChrome())
            retorno.put("BROWSER", "Google Chrome");
        else if (webBrowser.isFirefox())
            retorno.put("BROWSER", "Mozilla Firefox");
        else if (webBrowser.isIE())
            retorno.put("BROWSER", "Microsoft Intener Explorer");
        else if (webBrowser.isEdge())
            retorno.put("BROWSER", "Microsoft Edge");
        else if (webBrowser.isSafari())
            retorno.put("BROWSER", "Apple Safari");
        else if (webBrowser.isOpera())
            retorno.put("BROWSER", "Opera");
        else
            retorno.put("BROWSER", "Indefinido");
        
        if (webBrowser.isWindows())
            retorno.put("SO", "Microsoft Windows");
        else if (webBrowser.isMacOSX())
            retorno.put("SO", "Mac OS X");
        else if (webBrowser.isLinux())
            retorno.put("SO", "Linux OS");
        else if (webBrowser.isAndroid())
            retorno.put("SO", "Android");
        else if (webBrowser.isIOS())
            retorno.put("SO", "Apple IOS");
        else if (webBrowser.isWindowsPhone())
            retorno.put("SO", "Microsoft Window Phone");
        else
            retorno.put("SO", "Indefinido");
                
        if (webBrowser.getAddress().equals("0:0:0:0:0:0:0:1"))
            retorno.put("IP", "127.0.0.1");
        else
            retorno.put("IP", webBrowser.getAddress());
        
        return retorno;
    }
    
    public static int getDias(Date fechaInicial, Date fechaFinal) {
        return (int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
    }
    
    public static boolean isUserMenu(Object nameMenu) {
        boolean retorno = false;
        String cadenaSql = "SELECT login "
                + "FROM menu_app_users "
                + "WHERE login = '" + getSessionUser() + "' "
                    + "AND name_menu = '" + nameMenu + "'";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()) {
                retorno = true;
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando conexión - " + SeveralProcesses.getSessionUser(), ex);
            }
        }
        
        return retorno;
    }
    
    public static String getCaracteresReemplazados(String cadena) {
        return cadena.replaceAll("[^\\w\\s\\.]","");
    }
    
    public static String getCodigosProveedores() {
        String retorno = "";
        String cadenaSql = "SELECT DISTINCT a.codigo_proveedor FROM profesionales_proveedores a WHERE a.codigo_profesional = " + getCodigoProfesional() + " AND a.activo = 1";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            while (rs.next()) {
                retorno += rs.getString(1) + ",";
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando conexión - " + getSessionUser(), ex);
            }
        }
        return retorno.substring(0, retorno.length() - 1);
    }
    
    public static String getCodigosProfesionales() {
        String retorno = "";
        String cadenaSql = "SELECT DISTINCT a.codigo_profesional "
                + "FROM profesionales_proveedores a "
                + "WHERE a.codigo_proveedor IN (" + getCodigosProveedores() + ") AND a.activo = 1";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            while (rs.next()) {
                retorno += rs.getString(1) + ",";
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando conexión - " + getSessionUser(), ex);
            }
        }
        return retorno.substring(0, retorno.length() - 1);
    }
    
    public static String getViewURL(String url) {
        if (url.contains("/"))
            return url.substring(0, url.indexOf("/"));
        else
            return url;
    }
    
    public static String getCodigoUsuarioDatoFuncionario() {
        String retorno = null;
        String cadenaSql = "SELECT a.codigo_usuarios_datos "
                + "FROM usuarios_datos a "
                + "INNER JOIN profesionales b ON b.tipo_id_profesional = a.tipo_id_usuario AND b.id_profesional = a.id_usuario "
                + "INNER JOIN profesionales_proveedores c ON c.codigo_profesional = b.codigo_profesional "
                + "INNER JOIN proveedores d ON d.codigo_proveedor = c.codigo_proveedor AND d.interno = 1 "
                + "INNER JOIN app_users_profesionales e ON e.codigo_profesional = b.codigo_profesional AND e.login = '" + getSessionUser() + "' "
                + "WHERE a.registro_actual = 1 "
                + "LIMIT 1";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if (rs.next()) {
                retorno = rs.getString(1);
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
            }
        }
        return retorno;
    }
    
    public static String getValorComponenteEncuesta(Component componente) {
        String retorno;
        if (componente.getClass().equals(Switch.class)) {
            if (((Switch)componente).getValue())
                retorno = "'SI'";
            else
                retorno = "'NO'";
        } else {
            retorno = getValorComponente(componente, true);
        }
        
        return retorno;
    }
    
    public static String getLoginEmpresaCliente(Object valor) {
        String retorno = null;
        if (UI.getCurrent().getSession().getAttribute(VariablesSesion.CODIGO_DOCENTE).equals("CLIENTE") && valor == null) {
            retorno = "'" + SeveralProcesses.getSessionUser() + "'";
        } else if (valor != null) {
            String cadenaSql = "SELECT "
                            + "GROUP_CONCAT(CONCAT(\"'\",a.login,\"'\") SEPARATOR ',')"
                        + "FROM app_users_empresas_clientes_sedes a "
                        + "INNER JOIN empresas_clientes_sedes b ON b.codigo_empresa_cliente_sede = a.codigo_empresa_cliente_sede "
                            + "AND b.codigo_empresa = " + valor;
            GestionDB objConnect = null;
            try {
                objConnect = new GestionDB();
                ResultSet rs = objConnect.consultar(cadenaSql);
                if (rs.next()) {
                    retorno = rs.getString(1);
                }
            } catch (NamingException | SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
            } finally {
                try {
                    if (objConnect != null) {
                        objConnect.desconectar();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
                }
            }
        }
        return retorno;
    }
    
    public static void activarVoz(Object codigoTurnoProfesional) {
        String cadenaSql = "UPDATE turnos_profesionales SET voz = 1 WHERE codigo_turno_profesional = " + codigoTurnoProfesional;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            objConnect.insertarActualizarBorrar(cadenaSql, false);
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
            }
        }
    }
    
    public static void cambiarLabelBtnHistorico(String codigoIngresoRegistro, Button btnHistorico) {
        btnHistorico.setStyleName(ValoTheme.BUTTON_TINY);
        String cadenaSql = "SELECT "
                + "IF(a.historia_cerrada = 1, 'CERRADA', 'ABIERTA') AS estado_historia, "
                + "a.fecha_cierre_historia "
                + "FROM ingresos_registros a "
                + "WHERE a.codigo_ingresos_registros = " + codigoIngresoRegistro;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if ( rs.next() ) {
                if (rs.getTimestamp("fecha_cierre_historia") == null)
                    btnHistorico.setCaption(rs.getString("estado_historia"));
                else
                    btnHistorico.setCaption(rs.getString("estado_historia") + " (" + new SimpleDateFormat("yyyy-MM-dd KK:mm:ss aa").format(rs.getTimestamp("fecha_cierre_historia")) + ")");
                if (rs.getString("estado_historia").equals("ABIERTA"))
                    btnHistorico.addStyleName("amarillo");
                else{
                    btnHistorico.addStyleName("azul");
                }
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
            }
        }
    }
    
    public static boolean isIngresoCerrado(String codigoIngreso) {
        String cadenaSql = "SELECT COUNT(*) AS cantidad "
                + "FROM ingresos_registros a "
                + "WHERE a.codigo_ingreso = " + codigoIngreso + " AND a.historia_cerrada = 0";
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            return rs.next() && rs.getInt(1) == 0;
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
            return false;
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
            }
        }
    }
    
    public static boolean isIgual(Object valor1, Object valor2) {
        if ( valor1 == null || valor2 == null )
            return valor1 == valor2;
        else
            return valor1.equals(valor2);
    }
    
    public static String getStringDateFormat(Object fecha, String formato) {
        if ( fecha == null )
            return null;
        else
            return new SimpleDateFormat(formato).format(fecha);
    }
    
    
    public static void addItemsComboDefault(ComboBox combo, String cadenaSql) {
        if (!cadenaSql.isEmpty()) {
            Integer contador = 0;
            Integer valor = 0;
            combo.removeAllItems();
            GestionDB objConnect = null;
            try {
                objConnect = new GestionDB();
                ResultSet rsCbb = objConnect.consultar(cadenaSql);
                if (rsCbb.next()) {
                    combo.addItem(rsCbb.getInt(1));
                    combo.setItemCaption(rsCbb.getInt(1), rsCbb.getString(2));
                    contador++;
                    valor = rsCbb.getInt(1);
                    while(rsCbb.next()){
                        combo.addItem(rsCbb.getInt(1));
                        combo.setItemCaption(rsCbb.getInt(1), rsCbb.getString(2));
                        contador++;
                        valor = rsCbb.getInt(1);
                    }
                }
                if (contador == 1)
                    combo.setValue(valor);
            } catch (SQLException | NullPointerException | NamingException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
                Notifications.getError(ex.getMessage());
            } finally {
                try {
                    if (objConnect != null)
                        objConnect.desconectar();
                } catch (SQLException ex) {
                    Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
                    Notifications.getError(ex.getMessage());
                }   
            }
        }
    }
    
    public static void addTextItemsComboDefault(ComboBox combo, String cadenaSql) {
        if (!cadenaSql.isEmpty()) {
            Integer contador = 0;
            String valor = null;
            combo.removeAllItems();
            GestionDB objConnect = null;
            try {
                objConnect = new GestionDB();
                ResultSet rsCbb = objConnect.consultar(cadenaSql);
                if (rsCbb.next()) {
                    combo.addItem(rsCbb.getString(1));
                    combo.setItemCaption(rsCbb.getString(1), rsCbb.getString(2));
                    contador++;
                    valor = rsCbb.getString(1);
                    while(rsCbb.next()){
                        combo.addItem(rsCbb.getString(1));
                        combo.setItemCaption(rsCbb.getString(1), rsCbb.getString(2));
                        contador++;
                        valor = rsCbb.getString(1);
                    }
                }
                if (contador == 1)
                    combo.setValue(valor);
            } catch (SQLException | NullPointerException | NamingException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
                Notifications.getError(ex.getMessage());
            } finally {
                try {
                    if (objConnect != null)
                        objConnect.desconectar();
                } catch (SQLException ex) {
                    Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
                    Notifications.getError(ex.getMessage());
                }
            }
        }
    }
    
    public static void addTextItemsComboNotDefault(ComboBox combo, String cadenaSql) {
        if (!cadenaSql.isEmpty()) {
            String valor = null;
            combo.removeAllItems();
            GestionDB objConnect = null;
            try {
                objConnect = new GestionDB();
                ResultSet rsCbb = objConnect.consultar(cadenaSql);
                if (rsCbb.next()) {
                    combo.addItem(rsCbb.getString(1));
                    combo.setItemCaption(rsCbb.getString(1), rsCbb.getString(2));
                    valor = rsCbb.getString(1);
                    while(rsCbb.next()){
                        combo.addItem(rsCbb.getString(1));
                        combo.setItemCaption(rsCbb.getString(1), rsCbb.getString(2));
                        valor = rsCbb.getString(1);
                    }
                }     
            } catch (SQLException | NullPointerException | NamingException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
                Notifications.getError(ex.getMessage());
            } finally {
                try {
                    if (objConnect != null)
                        objConnect.desconectar();
                } catch (SQLException ex) {
                    Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
                    Notifications.getError(ex.getMessage());
                }
            }
        }
    }
    
    public static void addItemsComboNotDefault(ComboBox combo, String cadenaSql) {
        if (!cadenaSql.isEmpty()) {
            combo.removeAllItems();
            GestionDB objConnect = null;
            try {
                objConnect = new GestionDB();
                ResultSet rsCbb = objConnect.consultar(cadenaSql);
                if (rsCbb.next()) {
                    combo.addItem(rsCbb.getInt(1));
                    combo.setItemCaption(rsCbb.getInt(1), rsCbb.getString(2));
                    while(rsCbb.next()){
                        combo.addItem(rsCbb.getInt(1));
                        combo.setItemCaption(rsCbb.getInt(1), rsCbb.getString(2));
                    }
                }
            } catch (SQLException | NullPointerException | NamingException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
                Notifications.getError(ex.getMessage());
            } finally {
                try {
                    if (objConnect != null)
                        objConnect.desconectar();
                } catch (SQLException ex) {
                    Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
                    Notifications.getError(ex.getMessage());
                }
            }
        }
    }
    
    public static void addItemsComboNotDefaultNotClear(ComboBox combo, String cadenaSql) {
        if (!cadenaSql.isEmpty()) {
            GestionDB objConnect = null;
            try {
                objConnect = new GestionDB();
                ResultSet rsCbb = objConnect.consultar(cadenaSql);
                if (rsCbb.next()) {
                    combo.addItem(rsCbb.getInt(1));
                    combo.setItemCaption(rsCbb.getInt(1), rsCbb.getString(2));
                    while(rsCbb.next()){
                        combo.addItem(rsCbb.getInt(1));
                        combo.setItemCaption(rsCbb.getInt(1), rsCbb.getString(2));
                    }
                }
            } catch (SQLException | NullPointerException | NamingException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
                Notifications.getError(ex.getMessage());
            } finally {
                try {
                    if (objConnect != null)
                        objConnect.desconectar();
                } catch (SQLException ex) {
                    Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
                    Notifications.getError(ex.getMessage());
                }
            }
        }
    }
    
    public static void addItemsTwinColSelect(TwinColSelect twinColSelect, String cadenaSql) {
        twinColSelect.removeAllItems();
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rsTwin = objConnect.consultar(cadenaSql);
            while(rsTwin.next()){
                twinColSelect.addItem(rsTwin.getInt(1));
                twinColSelect.setItemCaption(rsTwin.getInt(1), rsTwin.getString(2));
            }
        } catch (SQLException | NullPointerException | NamingException ex) {
            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, cadenaSql  + " - " + SeveralProcesses.getSessionUser(), ex);
            Notifications.getError(ex.getMessage());
        } finally {
            try {
                if (objConnect != null)
                    objConnect.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
                Notifications.getError(ex.getMessage());
            }
        }
    }
    
    public static Boolean isComponentRequired(ComponentContainer componentContainer) {
        
        ArrayList<ComponentContainer> listComponentContainer = new ArrayList<>();
        listComponentContainer.add(componentContainer);
        Boolean retorno = true;
//        int i = 0;
        while(!listComponentContainer.isEmpty() && retorno){
//        while(i < listComponentContainer.size() && retorno){
            Iterator<Component> componentIterator = listComponentContainer.get(0).iterator();

            while(componentIterator.hasNext() && retorno){
                Component next = componentIterator.next();
                
                if(validateComponent(next)){                    
                    retorno = valueComponentValidate(next, retorno);
                } else {
                    if (next.getClass().equals(Panel.class) || next.getClass().equals(TwinColSelectCustom.class)) {
                        Component content = ((Panel) next).getContent();
                        if(validateComponent(content)) {
                            retorno = valueComponentValidate(content, retorno);
                        } else {
                            listComponentContainer.add((ComponentContainer) content);
                        }
                    } else {
                        listComponentContainer.add((ComponentContainer) next);
                    }
                }
            }
//            i++;
            listComponentContainer.remove(0);
        }
        if (!retorno)
            Notifications.getError("FALTA INFORMACIÓN REQUERIDA");
        return retorno;
    }
    
    public static Boolean valueComponentValidate(Component next, Boolean retorno) {
                    
        if ( next.getClass().equals(TextField.class) || next.getClass().equals(TextFieldCustom.class) 
                || next.getClass().equals(NumberField.class) || next.getClass().equals(NumberFieldCustom.class)
                || next.getClass().equals(AutocompleteTextField.class) || next.getClass().equals(TextFieldMask.class) ) {
            TextField textField = (TextField) next;
            if (!textField.isValid()){
                textField.setRequiredError("El valor de " + textField.getCaption().trim() + 
                        " no puede ser vacio o nulo");
                textField.setImmediate(true);
                textField.focus();
                retorno = false;
            }
        }
        
        if(next.getClass() == ComboBox.class || next.getClass() == ComboBoxCustom.class) {
            ComboBox comboBox = (ComboBox) next;
            if(!comboBox.isValid()) {
                if (comboBox.getCaption() != null)
                    comboBox.setRequiredError("El valor de " + comboBox.getCaption().trim() + " no puede ser vacio o nulo");
                else
                    comboBox.setRequiredError("El valor no puede ser vacio o nulo");
                comboBox.setImmediate(true);
                comboBox.focus();
                retorno = false;
            }
        }
        
        if(next.getClass().equals( PasswordField.class)) {
            PasswordField passwordField = (PasswordField) next;
            if(!passwordField.isValid()){
                passwordField.setRequiredError("El valor de " + passwordField.getCaption().trim() + 
                        " no puede ser vacio o nulo");
                passwordField.setImmediate(true);
                passwordField.focus();
                retorno = false;
            }
        }
        
        if(next.getClass().equals(TextArea.class) || next.getClass().equals(TextAreaCustom.class)) {
            TextArea textArea = (TextArea) next;
            if(!textArea.isValid()){
                textArea.setRequiredError("El valor de " + textArea.getCaption().trim() + 
                        " no puede ser vacio o nulo");
                textArea.setImmediate(true);
                textArea.focus();
                retorno = false;
            }
        }
        
        if(next.getClass().equals(CheckBox.class)) {
            CheckBox checkBox = (CheckBox) next;
            if(!checkBox.isValid()){
                checkBox.setRequiredError("El valor de " + checkBox.getCaption().trim() + 
                        " no puede ser vacio o nulo");
                checkBox.setImmediate(true);
                checkBox.focus();
                retorno = false;
            }
        }
        
        if(next.getClass().equals(TwinColSelect.class)) {
            TwinColSelect twinColSelect = (TwinColSelect) next;
            if(!twinColSelect.isValid()){
                twinColSelect.setRequiredError("El valor de " + twinColSelect.getCaption() + 
                        " no puede ser vacio o nulo");
                twinColSelect.setImmediate(true);
                twinColSelect.focus();
                retorno = false;
            }
        }
        
        if(next.getClass().equals(OptionGroup.class)) {
            OptionGroup optionGroup = (OptionGroup) next;
            if(!optionGroup.isValid()){
                if (optionGroup.getCaption() == null)
                    optionGroup.setRequiredError("El valor de Componente no puede ser vacio o nulo");
                else
                    optionGroup.setRequiredError("El valor de " + optionGroup.getCaption().trim() + " no puede ser vacio o nulo");
                optionGroup.setImmediate(true);
                optionGroup.focus();
                retorno = false;
            }
        }
        
        if(next.getClass().equals(PopupDateField.class)) {
            PopupDateField popupDateField = (PopupDateField) next;
            if(!popupDateField.isValid()){
                popupDateField.setRequiredError("El valor de " + popupDateField.getCaption().trim() + 
                        " no puede ser vacio o nulo");
                popupDateField.setImmediate(true);
                popupDateField.focus();
                retorno = false;
            }
        }
        
        if(next.getClass().equals(LabelClick.class)) {
            retorno =  true;
        }
        
        return retorno;
    }
    
    public static Object getCodigoDocenteEnSesion() {
        return UI.getCurrent().getSession().getAttribute(VariablesSesion.CODIGO_DOCENTE);
    }
    
    public static void cerrarSesion() {
        UI.getCurrent().getSession().setAttribute(VariablesSesion.LOGIN, null);
        UI.getCurrent().getSession().setAttribute(VariablesSesion.NOMBRE_USUARIO, null);
        UI.getCurrent().getSession().setAttribute(VariablesSesion.CODIGO_USUARIO, null);
        UI.getCurrent().getSession().setAttribute(VariablesSesion.CODIGO_DOCENTE, null);
        UI.getCurrent().getSession().setAttribute(VariablesSesion.CODIGO_ESTUDAINTE, null);
        UI.getCurrent().getSession().close();
        UI.getCurrent().close();
        UI.getCurrent().getNavigator().navigateTo(Views.REGISTRARNOTAS);
    }
}
