
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class Asignatura {
    
    private int codigoAsignatura;
    private String nombreAsignatura;
    
    public Asignatura() {
    }

    public Asignatura(int codigoAsignatura, String nombreAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
        this.nombreAsignatura = nombreAsignatura;
    }

    public int getCodigoAsignatura() {
        return codigoAsignatura;
    }

    public void setCodigoAsignatura(int codigoAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    @Override
    public String toString() {
        return "Asignatura [codigoAsignatura=" + codigoAsignatura + ", nombreAsignatura=" + nombreAsignatura + "]";
    }
}
