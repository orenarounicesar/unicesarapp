
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponsePublicarNota {
    
    private ResponseInt publicarNota;

    public ResponsePublicarNota() {
    }

    public ResponsePublicarNota(ResponseInt publicarNota) {
        this.publicarNota = publicarNota;
    }

    public ResponseInt getPublicarNota() {
        return publicarNota;
    }

    public void setPublicarNota(ResponseInt publicarNota) {
        this.publicarNota = publicarNota;
    }

    @Override
    public String toString() {
        return "ResponsePublicarNota{" + "publicarNota=" + publicarNota + '}';
    }
    
}
