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
        
        System.out.println("isNotaPublicada");
        int codigoEstudianteAsignatura = 1;
        int codigoCorte = 1;
        RegistrarNotas instance = new RegistrarNotas();
        boolean expResult = true;
        boolean result = instance.isNotaPublicada(codigoEstudianteAsignatura, codigoCorte);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of isNotaAlmacenada method, of class RegistrarNotas.
     */
    @Test
    public void testIsNotaAlmacenada() {
        
        System.out.println("isNotaAlmacenada");
        int codigoEstudianteAsignatura = 1;
        int codigoCorte = 1;
        RegistrarNotas instance = new RegistrarNotas();
        boolean expResult = true;
        boolean result = instance.isNotaPublicada(codigoEstudianteAsignatura, codigoCorte);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
