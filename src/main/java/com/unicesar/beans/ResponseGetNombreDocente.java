
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseGetNombreDocente {
    private ResponseString getNombreDocente;

    public ResponseGetNombreDocente() {
    }

    public ResponseGetNombreDocente(ResponseString getNombreDocente) {
        this.getNombreDocente = getNombreDocente;
    }

    public ResponseString getGetNombreDocente() {
        return getNombreDocente;
    }

    public void setGetNombreDocente(ResponseString getNombreDocente) {
        this.getNombreDocente = getNombreDocente;
    }

    @Override
    public String toString() {
        return "ResponseGetNombreDocente{" + "getNombreDocente=" + getNombreDocente + '}';
    }
    
}
