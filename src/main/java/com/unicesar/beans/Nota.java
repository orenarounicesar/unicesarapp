
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class Nota {
    
    private int codigoEstudianteAsignatura;
    private String codigoUniversitario;
    private String nombreEstudiante;
    private String identificacion;
    private float nota;
    private boolean publicada;
    
    public Nota() {
    }

    public Nota(int codigoEstudianteAsignatura, String codigoUniversitario, String nombreEstudiante,
            String identificacion, float nota, boolean publicada) {
        this.codigoEstudianteAsignatura = codigoEstudianteAsignatura;
        this.codigoUniversitario = codigoUniversitario;
        this.nombreEstudiante = nombreEstudiante;
        this.identificacion = identificacion;
        this.nota = nota;
        this.publicada = publicada;
    }

    public int getCodigoEstudianteAsignatura() {
        return codigoEstudianteAsignatura;
    }

    public void setCodigoEstudianteAsignatura(int codigoEstudianteAsignatura) {
        this.codigoEstudianteAsignatura = codigoEstudianteAsignatura;
    }

    public String getCodigoUniversitario() {
        return codigoUniversitario;
    }

    public void setCodigoUniversitario(String codigoUniversitario) {
        this.codigoUniversitario = codigoUniversitario;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public boolean isPublicada() {
        return publicada;
    }

    public void setPublicada(boolean publicada) {
        this.publicada = publicada;
    }

    @Override
    public String toString() {
        return "Nota [codigoEstudianteAsignatura=" + codigoEstudianteAsignatura + ", codigoUniversitario="
                + codigoUniversitario + ", identificacion=" + identificacion + ", nombreEstudiante=" + nombreEstudiante
                + ", nota=" + nota + ", publicada=" + publicada + "]";
    }
    
}
