
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseBoolean {
    private boolean respuesta;

    public ResponseBoolean() {
    }

    public ResponseBoolean(boolean respuesta) {
        this.respuesta = respuesta;
    }

    public boolean isRespuesta() {
        return respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseBoolean [respuesta=" + respuesta + "]";
    }
}
