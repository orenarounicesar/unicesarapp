/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicesar.utils;

import com.unicesar.beans.NotaDatos;

/**
 *
 * @author orenaro
 */
public class GestionarNota extends Thread {

    private final int codigoEstudianteAsignatura;
    private final int codigoCorte;
    private final NotaDatos notaDatos;
    
    public GestionarNota(int codigoEstudianteAsignatura, int codigoCorte, NotaDatos notaDatos) {
        this.codigoEstudianteAsignatura = codigoEstudianteAsignatura;
        this.codigoCorte = codigoCorte;
        this.notaDatos = notaDatos;
    }
    
    @Override
    public void run() {
        System.out.println(notaDatos);
        if ( notaDatos == null )
            Enrutador.deleteNota(codigoEstudianteAsignatura, codigoCorte);
        else
            Enrutador.agregarNota(notaDatos);
    }
}
