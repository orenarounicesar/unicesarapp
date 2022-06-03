
package com.unicesar.utils;

import com.unicesar.beans.Asignatura;
import com.unicesar.beans.Corte;
import com.unicesar.beans.Nota;
import com.unicesar.beans.NotaDatos;
import com.unicesar.beans.Usuario;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class Enrutador {
    
    
    public static Usuario getVendedorById(String login, String password) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/usuarios");
        Response response = target
                .queryParam("login", login)
                .queryParam("password", password)
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        Usuario usuario = response.readEntity(Usuario.class);
        response.close();
        client.close();
        return usuario;
    }
    
    public static String getNombreDocente(int codigoDocente) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/docentes");
        Response response = target
                .queryParam("codigoDocente", codigoDocente)
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        String nombreDocente = response.readEntity(String.class);
        response.close();
        client.close();
        return nombreDocente;
    }
    
    public static Corte getCorte(int codigoCorte) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/cortes");
        Response response = target
                .queryParam("codigoCorte", codigoCorte)
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        Corte corte = response.readEntity(Corte.class);
        response.close();
        client.close();
        return corte;
    }
    
    public static List<Asignatura> getAsignaturasDocente(int codigoDocente) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/asignaturas");
        Response response = target
                .queryParam("codigoDocente", codigoDocente)
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        List<Asignatura> lista = response.readEntity(new GenericType<List<Asignatura>>(){});
        response.close();
        client.close();
        return lista;
    }
    
    public static List<Nota> getNotasAsignatura(int codigoAsignatura, int codigoCorte) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/notas");
        Response response = target
                .queryParam("codigoAsignatura", codigoAsignatura)
                .queryParam("codigoCorte", codigoCorte)
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        List<Nota> lista = response.readEntity(new GenericType<List<Nota>>(){});
        response.close();
        client.close();
        return lista;
    }
    
    public static int deleteNota(int codigoEstudianteAsignatura, int codigoCorte) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/notas");
        Response response = target
                .queryParam("codigoEstudianteAsignatura", codigoEstudianteAsignatura)
                .queryParam("codigoCorte", codigoCorte)
                .request()
                .delete();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        int respuesta = response.readEntity(Integer.class);
        response.close();
        client.close();
        return respuesta;
    }
    
    
    public static int agregarNota(NotaDatos notaDatos) {
        System.out.println("Entró");
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/notas");
        Response response = target
                .request()
                .post(Entity.entity(notaDatos, "application/json"));
        System.out.println(response);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        int respuesta = response.readEntity(Integer.class);
        response.close();
        client.close();
        return respuesta;
    }
    
    
}
