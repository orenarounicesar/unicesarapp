
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseInt {
    private int codigoRespuesta;

    public ResponseInt() {
    }

    public ResponseInt(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    @Override
    public String toString() {
        return "ResponseInt [codigoRespuesta=" + codigoRespuesta + "]";
    }
}
