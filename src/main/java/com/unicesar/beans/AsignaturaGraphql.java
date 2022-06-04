
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class AsignaturaGraphql {
    
    private Asignatura classById;
    
    public AsignaturaGraphql() {
    }

    public AsignaturaGraphql(Asignatura classById) {
        this.classById = classById;
    }

    public Asignatura getClassById() {
        return classById;
    }

    public void setClassById(Asignatura classById) {
        this.classById = classById;
    }

    @Override
    public String toString() {
        return "AsignaturaGraphql{" + "classById=" + classById + '}';
    }
    
    
}
