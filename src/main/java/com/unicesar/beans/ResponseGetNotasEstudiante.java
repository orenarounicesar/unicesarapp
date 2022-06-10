
package com.unicesar.beans;

import java.util.List;

/**
 *
 * @author orenaro
 */
public class ResponseGetNotasEstudiante {
    
    private List<NotaEstudiante> getNotasEstudiante;

    public ResponseGetNotasEstudiante() {
    }

    public ResponseGetNotasEstudiante(List<NotaEstudiante> getNotasEstudiante) {
        this.getNotasEstudiante = getNotasEstudiante;
    }

    public List<NotaEstudiante> getGetNotasEstudiante() {
        return getNotasEstudiante;
    }

    public void setGetNotasEstudiante(List<NotaEstudiante> getNotasEstudiante) {
        this.getNotasEstudiante = getNotasEstudiante;
    }

    @Override
    public String toString() {
        return "ResponseGetNotasEstudiante{" + "getNotasEstudiante=" + getNotasEstudiante + '}';
    }
    
}
