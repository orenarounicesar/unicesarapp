
package com.unicesar.beans;

import java.sql.Date;

/**
 *
 * @author orenaro
 */
public class Email {
    private int codigoEmail;
    private int codigoEstudianteAsignatura;
    private String emailOrigen;
    private String emailOrigenPassword;
    private String emailDestino;
    private String emailAsunto;
    private String emailCuerpo;
    private Date fecha;
    
    public Email() {
    }

    public Email(int codigoEmail, int codigoEstudianteAsignatura, String emailOrigen, String emailOrigenPassword,
            String emailDestino, String emailAsunto, String emailCuerpo, Date fecha) {
        this.codigoEmail = codigoEmail;
        this.codigoEstudianteAsignatura = codigoEstudianteAsignatura;
        this.emailOrigen = emailOrigen;
        this.emailOrigenPassword = emailOrigenPassword;
        this.emailDestino = emailDestino;
        this.emailAsunto = emailAsunto;
        this.emailCuerpo = emailCuerpo;
        this.fecha = fecha;
    }

    public Email(int codigoEstudianteAsignatura, String emailOrigen, String emailOrigenPassword,
            String emailDestino, String emailAsunto, String emailCuerpo, Date fecha) {
        this.codigoEstudianteAsignatura = codigoEstudianteAsignatura;
        this.emailOrigen = emailOrigen;
        this.emailOrigenPassword = emailOrigenPassword;
        this.emailDestino = emailDestino;
        this.emailAsunto = emailAsunto;
        this.emailCuerpo = emailCuerpo;
        this.fecha = fecha;
    }

    public int getCodigoEmail() {
        return codigoEmail;
    }

    public void setCodigoEmail(int codigoEmail) {
        this.codigoEmail = codigoEmail;
    }

    public int getCodigoEstudianteAsignatura() {
        return codigoEstudianteAsignatura;
    }

    public void setCodigoEstudianteAsignatura(int codigoEstudianteAsignatura) {
        this.codigoEstudianteAsignatura = codigoEstudianteAsignatura;
    }

    public String getEmailOrigen() {
        return emailOrigen;
    }

    public void setEmailOrigen(String emailOrigen) {
        this.emailOrigen = emailOrigen;
    }

    public String getEmailOrigenPassword() {
        return emailOrigenPassword;
    }

    public void setEmailOrigenPassword(String emailOrigenPassword) {
        this.emailOrigenPassword = emailOrigenPassword;
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    public String getEmailAsunto() {
        return emailAsunto;
    }

    public void setEmailAsunto(String emailAsunto) {
        this.emailAsunto = emailAsunto;
    }

    public String getEmailCuerpo() {
        return emailCuerpo;
    }

    public void setEmailCuerpo(String emailCuerpo) {
        this.emailCuerpo = emailCuerpo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Email [codigoEmail=" + codigoEmail + ", codigoEstudianteAsignatura=" + codigoEstudianteAsignatura
                + ", emailAsunto=" + emailAsunto + ", emailCuerpo=" + emailCuerpo + ", emailDestino=" + emailDestino
                + ", emailOrigen=" + emailOrigen + ", emailOrigenPassword=" + emailOrigenPassword + ", fecha=" + fecha
                + "]";
    }
}
