
package com.unicesar.utils;

import com.unicesar.beans.Asignatura;
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
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteTextField;
import io.aexp.nodes.graphql.GraphQLRequestEntity;
import io.aexp.nodes.graphql.GraphQLResponseEntity;
import io.aexp.nodes.graphql.GraphQLTemplate;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
    
    
    public static void confirmarSentencia (String mensaje)throws GestionDBException{
        if (!mensaje.substring(0, 4).equals("true")) {
            throw new GestionDBException(mensaje);            
        }
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
    
//    public static Date getFechaActualSinHoraServidor() {
//        Date retorno = null;
//        GestionDB objConnect = null;
//        try {
//            objConnect = new GestionDB();
//            ResultSet rs = objConnect.consultar("SELECT CURDATE() AS fecha");
//            if (rs.next()) {
//                retorno = rs.getDate("fecha");
//            }
//        } catch (SQLException | NamingException ex) {
//            Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, getSessionUser(), ex);
//        } finally {
//            try {
//                if (objConnect != null)
//                    objConnect.desconectar();
//            } catch (SQLException ex) {
//                Logger.getLogger(SeveralProcesses.class.getName()).log(Level.SEVERE, "Cerrando Conexión - " + getSessionUser(), ex);
//            }
//        }
//        return retorno;
//    }
    
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
    
    public static String getCaracteresReemplazados(String cadena) {
        return cadena.replaceAll("[^\\w\\s\\.]","");
    }
    
    public static String getViewURL(String url) {
        if (url.contains("/"))
            return url.substring(0, url.indexOf("/"));
        else
            return url;
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
    
    public static Object getCodigoEstudianteEnSesion() {
        return UI.getCurrent().getSession().getAttribute(VariablesSesion.CODIGO_ESTUDAINTE);
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
    
    
    public static GraphQLResponseEntity<Asignatura> callGraphQLService(String url, String query) throws IOException {
        GraphQLTemplate graphQLTemplate = new GraphQLTemplate();

        GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(StringUtils.join(url, "?query=", query))
                .request(Asignatura.class)
                .build();

        return graphQLTemplate.query(requestEntity, Asignatura.class);
    }
}
