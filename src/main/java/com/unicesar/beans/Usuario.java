
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class Usuario {
    private int codigoUsuario;
    private String login;
    private String nombreUsuario;
    private int codigoDocente;
    private int codigoEstudiante;
    
    public Usuario() {
    }

    public Usuario(int codigoUsuario, String login, String nombreUsuario, int codigoDocente, int codigoEstudiante) {
        this.codigoUsuario = codigoUsuario;
        this.login = login;
        this.nombreUsuario = nombreUsuario;
        this.codigoDocente = codigoDocente;
        this.codigoEstudiante = codigoEstudiante;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(int codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public int getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(int codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    @Override
    public String toString() {
        return "Usuario [codigoDocente=" + codigoDocente + ", codigoEstudiante=" + codigoEstudiante + ", codigoUsuario="
                + codigoUsuario + ", login=" + login + ", nombreUsuario=" + nombreUsuario + "]";
    }
    
    
    
}
