
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseIsNotaAlmacenada {
    
    private ResponseBoolean isNotaAlmacenada;

    public ResponseIsNotaAlmacenada() {
    }

    public ResponseIsNotaAlmacenada(ResponseBoolean isNotaAlmacenada) {
        this.isNotaAlmacenada = isNotaAlmacenada;
    }

    public ResponseBoolean getIsNotaAlmacenada() {
        return isNotaAlmacenada;
    }

    public void setIsNotaAlmacenada(ResponseBoolean isNotaAlmacenada) {
        this.isNotaAlmacenada = isNotaAlmacenada;
    }

    @Override
    public String toString() {
        return "ResponseIsNotaAlmacenada{" + "isNotaAlmacenada=" + isNotaAlmacenada + '}';
    }
    
}
