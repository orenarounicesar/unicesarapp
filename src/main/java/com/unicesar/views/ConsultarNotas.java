/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicesar.views;

import com.unicesar.beans.NotaEstudiante;
import com.unicesar.components.LabelClick;
import com.unicesar.components.TableWithFilterSplit;
import com.unicesar.utils.Enrutador;
import com.unicesar.utils.SeveralProcesses;
import com.unicesar.utils.Views;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;

/**
 *
 * @author orenaro
 */
public class ConsultarNotas extends VerticalSplitPanel implements View {

    private Label lblTitulo;
    private Label lblNombreDocente;
    private LabelClick lblAtras;
    private GridLayout layoutCabecera;
    private TableWithFilterSplit tblAsignaturas;
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        lblTitulo = new Label("Consulta de Notas");
        lblTitulo.setWidthUndefined();
        lblTitulo.setStyleName("titulo");
        lblTitulo.addStyleName("textoEnormeRojo");
        lblAtras = new LabelClick(VaadinIcons.ARROW_LEFT.getHtml() + " Atras", false);
        lblAtras.setWidthUndefined();
        lblAtras.layoutLabel.addLayoutClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
        });
        lblNombreDocente = new Label("Estudiante: <strong>" + getNombreEstudiante(Integer.valueOf( SeveralProcesses.getCodigoEstudianteEnSesion().toString() )) + "</strong>", ContentMode.HTML);
        lblNombreDocente.setWidthUndefined();
        
        layoutCabecera = new GridLayout(3, 1);
        layoutCabecera.addComponent(lblNombreDocente, 0, 0);
        layoutCabecera.addComponent(lblTitulo, 1, 0);
        layoutCabecera.addComponent(lblAtras.layoutLabel, 2, 0);
        layoutCabecera.setWidth("100%");
        layoutCabecera.setMargin(new MarginInfo(false, true, false, true));
        layoutCabecera.setSpacing(true);
        layoutCabecera.setComponentAlignment(lblTitulo, Alignment.MIDDLE_CENTER);
        layoutCabecera.setComponentAlignment(lblAtras.layoutLabel, Alignment.MIDDLE_RIGHT);
        layoutCabecera.setComponentAlignment(lblNombreDocente, Alignment.MIDDLE_LEFT);
        
        tblAsignaturas = new TableWithFilterSplit("corte,asignatura", "Listado de Asignaturas", true);
        tblAsignaturas.addContainerProperty("corte", Object.class, null, "Corte", null, Table.Align.CENTER);
        tblAsignaturas.addContainerProperty("codigo", Object.class, null, "Codigo", null, Table.Align.CENTER);
        tblAsignaturas.addContainerProperty("asignatura", Object.class, null, "Asignatura", null, Table.Align.CENTER);
        tblAsignaturas.addContainerProperty("nota", Object.class, null, "Nota", null, Table.Align.CENTER);
        tblAsignaturas.setSizeFull();
        tblAsignaturas.setStyleName("tablaasignaturas");
        tblAsignaturas.layoutContent.setSizeFull();
        tblAsignaturas.panel.setSizeFull();
        tblAsignaturas.panel.setStyleName("panelverde");
        tblAsignaturas.panel.addStyleName("bordeverde");
        
        
        addComponents(layoutCabecera, tblAsignaturas.panel);
        setSizeFull();
        setSplitPosition(40, Sizeable.Unit.PIXELS);
        setStyleName("fondoaplicacion");
        setLocked(true);
        
        cargarTblAsignaturas();
    }
    
    private String getNombreEstudiante(int codigoEstudiante) {
        return Enrutador.getEstudiante(codigoEstudiante).getNombreEstudiante();
    }
    
    
    
    private void cargarTblAsignaturas() {
        for ( NotaEstudiante notaEstudiante : Enrutador.getNotasEstudiante( Integer.valueOf(SeveralProcesses.getCodigoEstudianteEnSesion().toString()) ) ){
                tblAsignaturas.addItem(
                        new Object[]{
                            notaEstudiante.getNombreCorte(),
                            notaEstudiante.getCodigoAsignatura(),
                            notaEstudiante.getNombreAsignatura(),
                            notaEstudiante.getNota()
                        }, 
                        notaEstudiante.getCodigoEstudianteAsignatura()
                );
        }
    }
    
}
