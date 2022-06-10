
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseGetEstudiante {
    private Estudiante studentByCod;

    public ResponseGetEstudiante() {
    }

    public ResponseGetEstudiante(Estudiante studentByCod) {
        this.studentByCod = studentByCod;
    }

    public Estudiante getStudentByCod() {
        return studentByCod;
    }

    public void setStudentByCod(Estudiante studentByCod) {
        this.studentByCod = studentByCod;
    }

    @Override
    public String toString() {
        return "ResponseGetEstudiante{" + "studentByCod=" + studentByCod + '}';
    }
    
}
