/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicesar.views;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author orenaro
 */
public class RegistrarNotasIT {
    
    public RegistrarNotasIT() {
    }

    /**
     * Test of isNotaPublicada method, of class RegistrarNotas.
     */
    @Test
    public void testIsNotaPublicada() {
        /*
        VERIFICAR EL ESTADO DE APROBACIÓN DE UNA NOTA
        DADO: Que el estudiante Orlando Arrieta con identificador 1 tiene la nota del primer corte publicada de la asignatura PROGRAMACION ORIENTADA A OBJETOS con codigo 1
        CUANDO: Se verifica el estado de publicación
        ENTONCES: Se obtiene un confirmación del estado
        */
        System.out.println("isNotaPublicada");
        //DADO: Que el estudiante Orlando Arrieta con identificador 1 tiene la nota del primer corte publicada de la asignatura PROGRAMACION ORIENTADA A OBJETOS con codigo 1
        int codigoEstudianteAsignatura = 1;
        int codigoCorte = 1;
        RegistrarNotas instance = new RegistrarNotas();
        boolean expResult = true;
        //CUANDO: Se verifica el estado de publicación
        boolean result = instance.isNotaPublicada(codigoEstudianteAsignatura, codigoCorte);
        //ENTONCES: Se obtiene un confirmación del estado
        assertEquals(expResult, result);
        
    }

    /**
     * Test of isNotaAlmacenada method, of class RegistrarNotas.
     */
    @Test
    public void testIsNotaAlmacenada() {
        /*
        VERIFICAR EL ESTADO DE ALMACENAMIENTO DE UNA NOTA
        DADO: Que el estudiante Orlando Arrieta con identificador 1 tiene la nota del primer corte almacenada de la asignatura PROGRAMACION ORIENTADA A OBJETOS con codigo 1
        CUANDO: Se verifica el estado de publicación
        ENTONCES: Se obtiene un confirmación del estado
        */
        System.out.println("isNotaAlmacenada");
        int codigoEstudianteAsignatura = 1;
        int codigoCorte = 1;
        RegistrarNotas instance = new RegistrarNotas();
        boolean expResult = true;
        boolean result = instance.isNotaPublicada(codigoEstudianteAsignatura, codigoCorte);
        assertEquals(expResult, result);
        
    }
    
}
