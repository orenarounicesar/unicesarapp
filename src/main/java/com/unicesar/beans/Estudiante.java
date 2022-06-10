
package com.unicesar.beans;

import java.sql.Date;


/**
 *
 * @author orenaro
 */
public class Estudiante {
    private int codigoEstudiante;
    private String codigoUniversitario;
    private String tipoId;
    private String id;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private Date fechaNacimiento;
    private String direccion;
    private String telefono;
    private String email;
    
    public Estudiante() {
    }

    public Estudiante(int codigoEstudiante, String codigoUniversitario, String tipoId, String id, String nombre1,
            String nombre2, String apellido1, String apellido2, Date fechaNacimiento, String direccion, String telefono,
            String email) {
        this.codigoEstudiante = codigoEstudiante;
        this.codigoUniversitario = codigoUniversitario;
        this.tipoId = tipoId;
        this.id = id;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public int getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(int codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public String getCodigoUniversitario() {
        return codigoUniversitario;
    }

    public void setCodigoUniversitario(String codigoUniversitario) {
        this.codigoUniversitario = codigoUniversitario;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreEstudiante() {
        return nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;
    }
    
    @Override
    public String toString() {
        return "Estudiante [apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", codigoEstudiante="
                + codigoEstudiante + ", codigoUniversitario=" + codigoUniversitario + ", direccion=" + direccion
                + ", email=" + email + ", fechaNacimiento=" + fechaNacimiento + ", id=" + id + ", nombre1=" + nombre1
                + ", nombre2=" + nombre2 + ", telefono=" + telefono + ", tipoId=" + tipoId + "]";
    }
}
