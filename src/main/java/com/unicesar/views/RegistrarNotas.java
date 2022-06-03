/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicesar.views;

import com.unicesar.beans.Asignatura;
import com.unicesar.beans.Corte;
import com.unicesar.beans.Nota;
import com.unicesar.beans.NotaDatos;
import com.unicesar.businesslogic.GestionDB;
import com.unicesar.businesslogic.GestionDBException;
import com.unicesar.components.LabelClick;
import com.unicesar.components.NumberFieldCustom;
import com.unicesar.components.TableWithFilterSplit;
import com.unicesar.utils.EmailSender;
import com.unicesar.utils.Enrutador;
import com.unicesar.utils.GestionarNota;
import com.unicesar.utils.Settings;
import com.unicesar.utils.SeveralProcesses;
import com.unicesar.utils.StandarEmailSender;
import com.unicesar.utils.Views;
import com.vaadin.data.Property;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author orenaro
 */
public class RegistrarNotas extends VerticalSplitPanel implements View {

    private Label lblTitulo;
    private Label lblNombreDocente;
    private Label lblCorte;
    private LabelClick lblSalir;
    private Button btnPublicar;
    private GridLayout layoutCabecera;
    private TableWithFilterSplit tblAsignaturas;
    private TableWithFilterSplit tblEstudiantes;
    private HorizontalSplitPanel layoutTablas;
    
    private String cadenaSql;
    private String nombreAsignaturaSeleccionada, nombreCorte;
    private final int codigoCorte = 1;
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        lblTitulo = new Label("Registro de Notas");
        lblTitulo.setWidthUndefined();
        lblTitulo.setStyleName("titulo");
        lblTitulo.addStyleName("textoEnormeRojo");
        lblSalir = new LabelClick(VaadinIcons.ARROW_LEFT.getHtml() + " Atras", false);
        lblSalir.setWidthUndefined();
        lblSalir.layoutLabel.addLayoutClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
        });
        lblNombreDocente = new Label("Docente: <strong>" + Enrutador.getNombreDocente((int) SeveralProcesses.getCodigoDocenteEnSesion()) + "</strong>", ContentMode.HTML);
        lblNombreDocente.setWidthUndefined();
        setValorLblCorte();
        btnPublicar = new Button("PUBLICAR", FontAwesome.EYE);
        btnPublicar.setStyleName("verde");
        btnPublicar.setEnabled(false);
        btnPublicar.addClickListener(e -> {
            publicarNotasAsignatura();
        });
        
        layoutCabecera = new GridLayout(3, 2);
        layoutCabecera.addComponent(lblTitulo, 1, 0);
        layoutCabecera.addComponent(lblSalir.layoutLabel, 2, 0);
        layoutCabecera.addComponent(lblNombreDocente, 0, 1);
        layoutCabecera.addComponent(lblCorte, 1, 1);
        layoutCabecera.addComponent(btnPublicar, 2, 1);
        layoutCabecera.setWidth("100%");
        layoutCabecera.setMargin(new MarginInfo(false, true, false, true));
        layoutCabecera.setSpacing(true);
        layoutCabecera.setComponentAlignment(lblTitulo, Alignment.MIDDLE_CENTER);
        layoutCabecera.setComponentAlignment(lblSalir.layoutLabel, Alignment.MIDDLE_RIGHT);
        layoutCabecera.setComponentAlignment(lblNombreDocente, Alignment.MIDDLE_LEFT);
        layoutCabecera.setComponentAlignment(lblCorte, Alignment.MIDDLE_CENTER);
        layoutCabecera.setComponentAlignment(btnPublicar, Alignment.TOP_RIGHT);
        
        tblAsignaturas = new TableWithFilterSplit("asignatura", "Listado de Asignaturas", true);
        tblAsignaturas.addContainerProperty("codigo", Object.class, null, "Codigo", null, Table.Align.CENTER);
        tblAsignaturas.addContainerProperty("asignatura", Object.class, null, "Asignatura", null, Table.Align.CENTER);
        tblAsignaturas.setSizeFull();
        tblAsignaturas.setStyleName("tablaasignaturas");
        tblAsignaturas.layoutContent.setSizeFull();
        tblAsignaturas.panel.setSizeFull();
        tblAsignaturas.panel.setStyleName("panelverde");
        tblAsignaturas.panel.addStyleName("bordeverde");
        
        tblEstudiantes = new TableWithFilterSplit("estudiante", "Listado de Estudiantes", true);
        tblEstudiantes.addContainerProperty("codigo", Object.class, null, "Codigo", null, Table.Align.CENTER);
        tblEstudiantes.addContainerProperty("estudiante", Object.class, null, "Estudiante", null, Table.Align.CENTER);
        tblEstudiantes.addContainerProperty("id", Object.class, null, "Identificación", null, Table.Align.CENTER);
        tblEstudiantes.addContainerProperty("nota", NumberFieldCustom.class, null, "Nota", null, Table.Align.CENTER);
        tblEstudiantes.setSizeFull();
        tblEstudiantes.setStyleName("tablafilasdelgadascomponente");
        tblEstudiantes.layoutContent.setSizeFull();
        tblEstudiantes.panel.setSizeFull();
        tblEstudiantes.panel.setStyleName("panelverde");
        tblEstudiantes.panel.addStyleName("bordeverde");
        
        layoutTablas = new HorizontalSplitPanel(tblAsignaturas.panel, tblEstudiantes.panel);
        layoutTablas.setSizeFull();
        layoutTablas.setSplitPosition(500, Sizeable.Unit.PIXELS);
        
        addComponents(layoutCabecera, layoutTablas);
        setSizeFull();
        setSplitPosition(85, Sizeable.Unit.PIXELS);
        setStyleName("fondoaplicacion");
        setLocked(true);
        
        cargarTblAsignaturas();
        tblAsignaturas.addItemClickListener(e -> {
            cargarTblEstudiantes( e.getItem().getItemProperty("codigo").getValue() );
            btnPublicar.setEnabled(!isNotasPublicadas());
            nombreAsignaturaSeleccionada = e.getItem().getItemProperty("asignatura").getValue().toString();
        });
    }
    
    private void setValorLblCorte() {
        lblCorte = new Label();
        lblCorte.setWidthUndefined();
        lblCorte.setContentMode(ContentMode.HTML);
        Corte corte = Enrutador.getCorte(codigoCorte);
        lblCorte.setValue("<strong>" + corte.getNombreCorte() + "</strong> - Fecha Limite: " + "<strong>" + corte.getFecha() + "</strong>" );
        nombreCorte = corte.getNombreCorte();
    }
    
    private void cargarTblAsignaturas() {
        for ( Asignatura asignatura : Enrutador.getAsignaturasDocente((int) SeveralProcesses.getCodigoDocenteEnSesion()) ) {
            tblAsignaturas.addItem(
                new Object[]{
                    asignatura.getCodigoAsignatura(),
                    asignatura.getNombreAsignatura()
                }, 
                asignatura.getCodigoAsignatura()
            );
        }
    }
    
    private void cargarTblEstudiantes(Object codigoAsignatura) {
        tblEstudiantes.removeAllItems();
        for ( Nota nota : Enrutador.getNotasAsignatura((int) codigoAsignatura, codigoCorte) ) {
            NumberFieldCustom txtNota = new NumberFieldCustom(null, true, false, 0.00, 5.00, String.valueOf(nota.getNota()), "100%", false, true, null);
            txtNota.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
            txtNota.addStyleName("centrartexto");
            txtNota.setEnabled(!nota.isPublicada());
            txtNota.addValueChangeListener(new Property.ValueChangeListener() {
                private final int codigoEstudianteAsignatura = nota.getCodigoEstudianteAsignatura();
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    if ( event.getProperty().getValue() == null ) {
                        new GestionarNota(codigoEstudianteAsignatura, codigoCorte, null).start();
                    } else {
                        new GestionarNota(
                                0,
                                0,
                                new NotaDatos(codigoEstudianteAsignatura, codigoCorte, Float.valueOf(event.getProperty().getValue().toString()))
                        ).start();
                    }
                       
                }
            });
            if ( tblEstudiantes.size() == 0 )
                txtNota.focus();
            tblEstudiantes.addItem(
                    new Object[]{
                        nota.getCodigoUniversitario(), 
                        nota.getNombreEstudiante(), 
                        nota.getIdentificacion(), 
                        txtNota
                    }, 
                    nota.getCodigoEstudianteAsignatura()
            );
        }
    }
    
    private void publicarNotasAsignatura() {
        if ( isNotasAlmacenadasAsignatura() ) {
            publicarNotas();
        } else {
            Notification.show("Debe diligenciar las notas de TODOS los estudiantes de la ASIGNATURA\npara poder publicar", Notification.Type.ERROR_MESSAGE);
        }
    }
    
    private boolean isNotasAlmacenadasAsignatura() {
        boolean retorno = true;
        for ( Object itemId : tblEstudiantes.getItemIds() ) {
            retorno = isNotaAlmacenada(Integer.valueOf(itemId.toString()), codigoCorte);
            if ( !retorno )
                break;
        }
        return retorno;
    }
    
    private boolean isNotaAlmacenada(int codigoEstudianteAsignatura, int codigoCorte) {
        cadenaSql = "SELECT a.codigo_nota "
                + "FROM notas a "
                + "WHERE a.codigo_estudiante_asignatura = " + codigoEstudianteAsignatura + " AND a.codigo_corte = " + codigoCorte;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if ( rs.next() )
                return true;
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
            }
        }
        return false;
    }
    
    private boolean isNotasPublicadas() {
        boolean retorno = true;
        for ( Object itemId : tblEstudiantes.getItemIds() ) {
            retorno = isNotaPublicada(Integer.valueOf(itemId.toString()), codigoCorte);
            if ( !retorno )
                break;
        }
        return retorno;
    }
    
    public boolean isNotaPublicada(int codigoEstudianteAsignatura, int codigoCorte) {
        cadenaSql = "SELECT a.publicada "
                + "FROM notas a "
                + "WHERE a.codigo_estudiante_asignatura = " + codigoEstudianteAsignatura + " AND a.codigo_corte = " + codigoCorte;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if ( rs.next() )
                return rs.getBoolean(1);
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
            }
        }
        return false;
    }
    
    private void publicarNotas() {
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            objConnect.begin();
            for ( Object itemId : tblEstudiantes.getItemIds() ) {
                publicarNota(objConnect, Integer.valueOf(itemId.toString()), codigoCorte);
            }
            objConnect.commit();
            Notification.show("Publicación Exitosa", Notification.Type.ERROR_MESSAGE);
            btnPublicar.setEnabled(false);
            for ( Object itemId : tblEstudiantes.getItemIds() ) {
                new StandarEmailSender(
                        new EmailSender(
                            Settings.EMAILORIGEN, 
                            Settings.EMAILPASSWORD, 
                            getEmailEstuadiante(itemId), 
                            "Publicación de Notas - Asignatura " + nombreAsignaturaSeleccionada,
                            "Buenas\n\n"
                                    + "Se informa que las notas de la asignatura " + nombreAsignaturaSeleccionada + " para el " + nombreCorte + " fueron publicadas",
                            null, 
                            null,
                            null,
                            null
                        )
                ).start();
            }
        } catch (NamingException | SQLException | GestionDBException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
            try {
                if (objConnect != null) {
                    objConnect.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Rollback - " + SeveralProcesses.getSessionUser(), ex1);
            }
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
            }
        }
    }
    
    private void publicarNota(GestionDB objConnect, int codigoEstudianteAsignatura, int codigoCorte) throws SQLException, GestionDBException {
        cadenaSql = "UPDATE notas a "
                + "SET a.publicada = 1 "
                + "WHERE a.codigo_estudiante_asignatura = " + codigoEstudianteAsignatura + " AND a.codigo_corte = " + codigoCorte;
        SeveralProcesses.confirmarSentencia(objConnect.insertarActualizarBorrar(cadenaSql, false));
    }

    private String getEmailEstuadiante(Object itemId) {
        cadenaSql = "SELECT a.email "
                + "FROM datos_personales a "
                + "INNER JOIN estudiantes b ON b.codigo_dato_personal = a.codigo_dato_personal "
                + "INNER JOIN estudiantes_asignaturas c ON c.codigo_estudiante = b.codigo_estudiante AND c.codigo_estudiante_asignatura = " + itemId
                ;
        GestionDB objConnect = null;
        try {
            objConnect = new GestionDB();
            ResultSet rs = objConnect.consultar(cadenaSql);
            if ( rs.next() )
                return rs.getString(1);
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, cadenaSql + " - " + SeveralProcesses.getSessionUser(), ex);
        } finally {
            try {
                if (objConnect != null) {
                    objConnect.desconectar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cerrando Conexión - " + SeveralProcesses.getSessionUser(), ex);
            }
        }
        return null;
    }
}
