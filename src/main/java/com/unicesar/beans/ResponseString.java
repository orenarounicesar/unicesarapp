
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseString {
    private String respuesta;

    public ResponseString() {
    }

    public ResponseString(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseString [respuesta=" + respuesta + "]";
    }
}
