
package com.unicesar.windows;

import com.unicesar.beans.Nota;
import com.unicesar.beans.NotaDatos;
import com.unicesar.components.NumberFieldCustom;
import com.unicesar.components.TableWithFilterSplit;
import com.unicesar.utils.Enrutador;
import com.unicesar.utils.GestionarNota;
import com.vaadin.data.Property;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author orenaro
 */
public class Asistencias extends Window {

    private final TableWithFilterSplit tblEstudiantes;
    
    public Asistencias(int codigoAsignatura, String nombreAsignatura, int codigoCorte) {
        super("Toma de Asistencia para la Asignatura " + nombreAsignatura);
        
        tblEstudiantes = new TableWithFilterSplit("estudiante", "Listado de Estudiantes", true);
        tblEstudiantes.addContainerProperty("codigo", Object.class, null, "Codigo", null, Table.Align.CENTER);
        tblEstudiantes.addContainerProperty("estudiante", Object.class, null, "Estudiante", null, Table.Align.CENTER);
        tblEstudiantes.addContainerProperty("id", Object.class, null, "Identificación", null, Table.Align.CENTER);
        tblEstudiantes.addContainerProperty("asistencia", CheckBox.class, null, "Nota", null, Table.Align.CENTER);
        tblEstudiantes.setSizeFull();
        tblEstudiantes.setStyleName("tablafilasdelgadascomponente");
        tblEstudiantes.layoutContent.setSizeFull();
        tblEstudiantes.panel.setSizeFull();
        tblEstudiantes.panel.setStyleName("panelverde");
        tblEstudiantes.panel.addStyleName("bordeverde");
        
        cargarTblEstudiantes(codigoAsignatura, codigoCorte);
        
        setContent(tblEstudiantes.panel);
        
        setWidth("80%");
        setHeight("80%");
        setResizable(true);
        setClosable(true);
        center();
        setModal(true);
    }
    
    private void cargarTblEstudiantes(int codigoAsignatura, int codigoCorte) {
        tblEstudiantes.removeAllItems();
        for ( Nota nota : Enrutador.getNotasAsignatura((int) codigoAsignatura, codigoCorte) ) {
            CheckBox chkAsistencia = new CheckBox(null, false);
            chkAsistencia.addValueChangeListener(new Property.ValueChangeListener() {
                private final int codigoEstudianteAsignatura = nota.getCodigoEstudianteAsignatura();
                private final int codigoAsignaturaLocal = codigoAsignatura;
                private int codigoAsistencia;
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    if ( (boolean)event.getProperty().getValue()) {
                        codigoAsistencia = Enrutador.crearAsistencia(codigoEstudianteAsignatura, codigoAsignaturaLocal);
                    } else {
                        Enrutador.eliminarAsistencia(codigoAsistencia);
                    }
                       
                }
            });
            if ( tblEstudiantes.size() == 0 )
                chkAsistencia.focus();
            tblEstudiantes.addItem(
                    new Object[]{
                        nota.getCodigoUniversitario(), 
                        nota.getNombreEstudiante(), 
                        nota.getIdentificacion(), 
                        chkAsistencia
                    }, 
                    nota.getCodigoEstudianteAsignatura()
            );
        }
    }
}
