
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseGetUsuario {
    private Usuario getUsuario;

    public ResponseGetUsuario() {
    }

    public ResponseGetUsuario(Usuario getUsuario) {
        this.getUsuario = getUsuario;
    }

    public Usuario getGetUsuario() {
        return getUsuario;
    }

    public void setGetUsuario(Usuario getUsuario) {
        this.getUsuario = getUsuario;
    }

    @Override
    public String toString() {
        return "ResponseGetUsuario{" + "getUsuario=" + getUsuario + '}';
    }
    
}
