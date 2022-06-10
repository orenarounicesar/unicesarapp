
package com.unicesar.utils;

public class Settings {
    public static int MINUTOSSESION;
    public static String RUTADIRECTORIOTEMPORAL;
    public static boolean VERIFICARCIERRECONEXION;
    public static int SEGUNDOSCERRARCONEXION;
    public static String EMAILORIGEN;
    public static String EMAILPASSWORD;
    public static String APIRESTNOTAS;
    public static String APIRESTNOTIFICACIONES;
    public static String ENDPOINTGRAPHQL;
    
    public Settings() {
            MINUTOSSESION = 30;
            VERIFICARCIERRECONEXION = true;
            SEGUNDOSCERRARCONEXION = 290;
            EMAILORIGEN = "orenarotest@gmail.com";
            EMAILPASSWORD = "asgloozhfevmcplc";
            ENDPOINTGRAPHQL = "http://localhost:5000/graphql";
        
    }
    
    public static void cargarEndPonts() {
        APIRESTNOTAS = "http://localhost:3100";
        APIRESTNOTIFICACIONES = "http://localhost:3101";
    }
}
