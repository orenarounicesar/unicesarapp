
package com.unicesar.beans;

import java.util.List;

/**
 *
 * @author orenaro
 */
public class ResponseGetAsignaturaDocente {
    
    private List<Asignatura> getAsignaturasDocente;

    public ResponseGetAsignaturaDocente() {
    }

    public ResponseGetAsignaturaDocente(List<Asignatura> getAsignaturasDocente) {
        this.getAsignaturasDocente = getAsignaturasDocente;
    }

    public List<Asignatura> getGetAsignaturasDocente() {
        return getAsignaturasDocente;
    }

    public void setGetAsignaturasDocente(List<Asignatura> getAsignaturasDocente) {
        this.getAsignaturasDocente = getAsignaturasDocente;
    }

    @Override
    public String toString() {
        return "ResponseGetAsignaturaDocente{" + "getAsignaturasDocente=" + getAsignaturasDocente + '}';
    }
    
}
