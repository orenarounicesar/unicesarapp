
package com.unicesar.beans;

import java.util.List;

/**
 *
 * @author orenaro
 */
public class ResponseGetNotasAsignatura {
    
    private List<Nota> getNotasAsignatura;

    public ResponseGetNotasAsignatura() {
    }

    public ResponseGetNotasAsignatura(List<Nota> getNotasAsignatura) {
        this.getNotasAsignatura = getNotasAsignatura;
    }

    public List<Nota> getGetNotasAsignatura() {
        return getNotasAsignatura;
    }

    public void setGetNotasAsignatura(List<Nota> getNotasAsignatura) {
        this.getNotasAsignatura = getNotasAsignatura;
    }

    @Override
    public String toString() {
        return "ResponseGetNotasAsignatura{" + "getNotasAsignatura=" + getNotasAsignatura + '}';
    }
    
    
}
