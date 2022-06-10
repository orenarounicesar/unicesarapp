
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseIsNotaPublicada {
    
    private ResponseBoolean isNotaPublicada;

    public ResponseIsNotaPublicada() {
    }

    public ResponseIsNotaPublicada(ResponseBoolean isNotaPublicada) {
        this.isNotaPublicada = isNotaPublicada;
    }

    public ResponseBoolean getIsNotaPublicada() {
        return isNotaPublicada;
    }

    public void setIsNotaPubli(ResponseBoolean isNotaAlmacenada) {
        this.isNotaPublicada = isNotaAlmacenada;
    }

    @Override
    public String toString() {
        return "ResponseIsNotaPublicada{" + "isNotaPublicada=" + isNotaPublicada + '}';
    }
    
}
