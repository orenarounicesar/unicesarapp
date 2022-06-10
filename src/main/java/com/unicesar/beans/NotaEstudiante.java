
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class NotaEstudiante {
    private int codigoEstudianteAsignatura;
    private String nombreCorte;
    private int codigoAsignatura;
    private String nombreAsignatura;
    private float nota;
    
    public NotaEstudiante() {
    }

    public NotaEstudiante(int codigoEstudianteAsignatura, String nombreCorte, int codigoAsignatura,
            String nombreAsignatura, float nota) {
        this.codigoEstudianteAsignatura = codigoEstudianteAsignatura;
        this.nombreCorte = nombreCorte;
        this.codigoAsignatura = codigoAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.nota = nota;
    }

    public int getCodigoEstudianteAsignatura() {
        return codigoEstudianteAsignatura;
    }

    public void setCodigoEstudianteAsignatura(int codigoEstudianteAsignatura) {
        this.codigoEstudianteAsignatura = codigoEstudianteAsignatura;
    }

    public String getNombreCorte() {
        return nombreCorte;
    }

    public void setNombreCorte(String nombreCorte) {
        this.nombreCorte = nombreCorte;
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

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "NotaEstudiante [codigoAsignatura=" + codigoAsignatura + ", codigoEstudianteAsignatura="
                + codigoEstudianteAsignatura + ", nombreAsignatura=" + nombreAsignatura + ", nombreCorte=" + nombreCorte
                + ", nota=" + nota + "]";
    }
}
