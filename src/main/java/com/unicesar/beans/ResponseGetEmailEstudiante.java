
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseGetEmailEstudiante {
    
    private ResponseString getEmailEstudiante;

    public ResponseGetEmailEstudiante() {
    }

    public ResponseGetEmailEstudiante(ResponseString getEmailEstudiante) {
        this.getEmailEstudiante = getEmailEstudiante;
    }

    public ResponseString getGetEmailEstudiante() {
        return getEmailEstudiante;
    }

    public void setGetEmailEstudiante(ResponseString getEmailEstudiante) {
        this.getEmailEstudiante = getEmailEstudiante;
    }

    @Override
    public String toString() {
        return "ResponseGetEmailEstudiante{" + "getEmailEstudiante=" + getEmailEstudiante + '}';
    }
    
    
}
