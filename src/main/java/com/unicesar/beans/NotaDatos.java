
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class NotaDatos {
    
    private int codigoEstudianteAsignatura;
    private int codigoCorte;
    private float nota;

    public NotaDatos() {
    }
    
    public NotaDatos(int codigoEstudianteAsignatura, int codigoCorte, float nota) {
        this.codigoEstudianteAsignatura = codigoEstudianteAsignatura;
        this.codigoCorte = codigoCorte;
        this.nota = nota;
    }

    public int getCodigoEstudianteAsignatura() {
        return codigoEstudianteAsignatura;
    }

    public void setCodigoEstudianteAsignatura(int codigoEstudianteAsignatura) {
        this.codigoEstudianteAsignatura = codigoEstudianteAsignatura;
    }

    public int getCodigoCorte() {
        return codigoCorte;
    }

    public void setCodigoCorte(int codigoCorte) {
        this.codigoCorte = codigoCorte;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "NotaDatos [codigoCorte=" + codigoCorte + ", codigoEstudianteAsignatura=" + codigoEstudianteAsignatura
                + ", nota=" + nota + "]";
    }
    
}