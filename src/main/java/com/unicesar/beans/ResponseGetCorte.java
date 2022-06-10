
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseGetCorte {
    
    private Corte getCorte;

    public ResponseGetCorte() {
    }

    public ResponseGetCorte(Corte getCorte) {
        this.getCorte = getCorte;
    }

    public Corte getGetCorte() {
        return getCorte;
    }

    public void setGetCorte(Corte getCorte) {
        this.getCorte = getCorte;
    }

    @Override
    public String toString() {
        return "ResponseGetCorte{" + "getCorte=" + getCorte + '}';
    }
    
}
