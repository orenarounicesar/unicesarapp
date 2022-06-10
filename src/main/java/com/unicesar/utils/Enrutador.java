
package com.unicesar.utils;

import com.unicesar.beans.Asignatura;
import com.unicesar.beans.Corte;
import com.unicesar.beans.Email;
import com.unicesar.beans.Estudiante;
import com.unicesar.beans.Nota;
import com.unicesar.beans.NotaDatos;
import com.unicesar.beans.NotaEstudiante;
import com.unicesar.beans.ResponseAgregarNota;
import com.unicesar.beans.ResponseCrearAsistencia;
import com.unicesar.beans.ResponseBoolean;
import com.unicesar.beans.ResponseDeleteNota;
import com.unicesar.beans.ResponseEliminarAsistencia;
import com.unicesar.beans.ResponseGetAsignaturaDocente;
import com.unicesar.beans.ResponseGetCorte;
import com.unicesar.beans.ResponseGetEstudiante;
import com.unicesar.beans.ResponseGetNombreDocente;
import com.unicesar.beans.ResponseGetNotasAsignatura;
import com.unicesar.beans.ResponseGetUsuario;
import com.unicesar.beans.ResponseInt;
import com.unicesar.beans.ResponseString;
import com.unicesar.beans.Usuario;
import com.unicesar.views.LoginView;
import io.aexp.nodes.graphql.GraphQLRequestEntity;
import io.aexp.nodes.graphql.GraphQLTemplate;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class Enrutador {
    
    
    public static Usuario getLogin(String login, String password) {
        try {
            GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
            
            GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(Settings.ENDPOINTGRAPHQL)
                .requestMethod(GraphQLTemplate.GraphQLMethod.MUTATE)
                .request(" query {\n" +
                    "	getUsuario(login:\"" + login + "\", password:\"" + password + "\"){\n" +
                    "        codigoUsuario,\n" +
                    "        login,\n" +
                    "        nombreUsuario,\n" +
                    "        codigoDocente,\n" +
                    "        codigoEstudiante\n" +
                    "	}\n" +
                    " }")
                .build();
            return graphQLTemplate.query(requestEntity, ResponseGetUsuario.class).getResponse().getGetUsuario();

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//    public static Usuario getLogin(String login, String password) {
//        Client client = ClientBuilder.newBuilder().build();
//        WebTarget target = client.target(Settings.APIRESTNOTAS + "/usuarios");
//        Response response = target
//                .queryParam("login", login)
//                .queryParam("password", password)
//                .request()
//                .get();
//
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
//        }
//
//        Usuario usuario = response.readEntity(Usuario.class);
//        response.close();
//        client.close();
//        return usuario;
//    }
    
    public static Estudiante getEstudiante(int codigoEstudiante) {
        try {
            GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
            
            GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(Settings.ENDPOINTGRAPHQL)
                .requestMethod(GraphQLTemplate.GraphQLMethod.MUTATE)
                .request("query {\n" +
                    "	studentByCod(cod:" + codigoEstudiante + "){\n" +
                    "        codigoEstudiante,\n" +
                    "        codigoUniversitario,\n" +
                    "        tipoId,\n" +
                    "        id,\n" +
                    "        nombre1,\n" +
                    "        nombre2,\n" +
                    "        apellido1,\n" +
                    "        apellido2,\n" +
                    "        fechaNacimiento,\n" +
                    "        direccion,\n" +
                    "        telefono,\n" +
                    "        email\n" +
                    "	}\n" +
                    " }")
                .build();
            return graphQLTemplate.query(requestEntity, ResponseGetEstudiante.class).getResponse().getStudentByCod();

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//    public static Estudiante getEstudiante(int codigoEstudiante) {
//        Client client = ClientBuilder.newBuilder().build();
//        WebTarget target = client.target(Settings.APIRESTNOTAS + "/estudiantes");
//        Response response = target
//                .queryParam("codigoEstudiante", codigoEstudiante)
//                .request()
//                .get();
//
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
//        }
//
//        Estudiante estudiante = response.readEntity(Estudiante.class);
//        response.close();
//        client.close();
//        return estudiante;
//    }
    
    public static String getNombreDocente(int codigoDocente) {
        try {
            GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
            
            GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(Settings.ENDPOINTGRAPHQL)
                .requestMethod(GraphQLTemplate.GraphQLMethod.MUTATE)
                .request("query {\n" +
                    "	getNombreDocente(codigoDocente:" + codigoDocente + "){\n" +
                    "        respuesta\n" +
                    "	}\n" +
                    " }")
                .build();
            return graphQLTemplate.query(requestEntity, ResponseGetNombreDocente.class).getResponse().getGetNombreDocente().getRespuesta();

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//    public static String getNombreDocente(int codigoDocente) {
//        Client client = ClientBuilder.newBuilder().build();
//        WebTarget target = client.target(Settings.APIRESTNOTAS + "/docentes");
//        Response response = target
//                .queryParam("codigoDocente", codigoDocente)
//                .request()
//                .get();
//
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
//        }
//
//        ResponseString responseString = response.readEntity(ResponseString.class);
//        response.close();
//        client.close();
//        return responseString.getRespuesta();
//    }
    
    public static Corte getCorte(int codigoCorte) {
        try {
            GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
            
            GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(Settings.ENDPOINTGRAPHQL)
                .requestMethod(GraphQLTemplate.GraphQLMethod.MUTATE)
                .request("query {\n" +
                    "	getCorte(codigoCorte:" + codigoCorte + "){\n" +
                    "        nombreCorte, \n" +
                    "        fecha\n" +
                    "	}\n" +
                    " }")
                .build();
            return graphQLTemplate.query(requestEntity, ResponseGetCorte.class).getResponse().getGetCorte();

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//    public static Corte getCorte(int codigoCorte) {
//        Client client = ClientBuilder.newBuilder().build();
//        WebTarget target = client.target(Settings.APIRESTNOTAS + "/cortes");
//        Response response = target
//                .queryParam("codigoCorte", codigoCorte)
//                .request()
//                .get();
//
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
//        }
//
//        Corte corte = response.readEntity(Corte.class);
//        response.close();
//        client.close();
//        return corte;
//    }
    
    public static List<Asignatura> getAsignaturasDocente(int codigoDocente) {
        try {
            GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
            
            GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(Settings.ENDPOINTGRAPHQL)
                .requestMethod(GraphQLTemplate.GraphQLMethod.MUTATE)
                .request("query {\n" +
                    "	getAsignaturasDocente(codigoDocente:" + codigoDocente + "){\n" +
                    "        codigoAsignatura, \n" +
                    "        nombreAsignatura\n" +
                    "	}\n" +
                    " }")
                .build();
            return graphQLTemplate.query(requestEntity, ResponseGetAsignaturaDocente.class).getResponse().getGetAsignaturasDocente();

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//    public static List<Asignatura> getAsignaturasDocente(int codigoDocente) {
//        Client client = ClientBuilder.newBuilder().build();
//        WebTarget target = client.target(Settings.APIRESTNOTAS + "/asignaturas");
//        Response response = target
//                .queryParam("codigoDocente", codigoDocente)
//                .request()
//                .get();
//
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
//        }
//
//        List<Asignatura> lista = response.readEntity(new GenericType<List<Asignatura>>(){});
//        response.close();
//        client.close();
//        return lista;
//    }
    
    public static List<Nota> getNotasAsignatura(int codigoAsignatura, int codigoCorte) {
        try {
            GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
            
            GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(Settings.ENDPOINTGRAPHQL)
                .requestMethod(GraphQLTemplate.GraphQLMethod.MUTATE)
                .request("query {\n" +
                    "	getNotasAsignatura(codigoAsignatura:" + codigoAsignatura + ", codigoCorte:" + codigoCorte + "){\n" +
                    "        codigoEstudianteAsignatura, \n" +
                    "        codigoUniversitario,\n" +
                    "        nombreEstudiante,\n" +
                    "        identificacion,\n" +
                    "        nota,\n" +
                    "        publicada\n" +
                    "	}\n" +
                    " }")
                .build();
            return graphQLTemplate.query(requestEntity, ResponseGetNotasAsignatura.class).getResponse().getGetNotasAsignatura();

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//    public static List<Nota> getNotasAsignatura(int codigoAsignatura, int codigoCorte) {
//        Client client = ClientBuilder.newBuilder().build();
//        WebTarget target = client.target(Settings.APIRESTNOTAS + "/notas");
//        Response response = target
//                .queryParam("codigoAsignatura", codigoAsignatura)
//                .queryParam("codigoCorte", codigoCorte)
//                .request()
//                .get();
//
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
//        }
//
//        List<Nota> lista = response.readEntity(new GenericType<List<Nota>>(){});
//        response.close();
//        client.close();
//        return lista;
//    }
    
    public static int deleteNota(int codigoEstudianteAsignatura, int codigoCorte) {
        try {
            GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
            
            GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(Settings.ENDPOINTGRAPHQL)
                .requestMethod(GraphQLTemplate.GraphQLMethod.MUTATE)
                .request("mutation{\n" +
                    "  deleteNote(codigoEstudianteAsignatura: " + codigoEstudianteAsignatura + ", codigoCorte: " + codigoCorte + "){\n" +
                    "      codigoRespuesta\n" +
                    "  }\n" +
                    "}")
                .build();
            return graphQLTemplate.mutate(requestEntity, ResponseDeleteNota.class).getResponse().getDeleteNote().getCodigoRespuesta();

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
//    public static int deleteNota(int codigoEstudianteAsignatura, int codigoCorte) {
//        Client client = ClientBuilder.newBuilder().build();
//        WebTarget target = client.target(Settings.APIRESTNOTAS + "/notas");
//        Response response = target
//                .queryParam("codigoEstudianteAsignatura", codigoEstudianteAsignatura)
//                .queryParam("codigoCorte", codigoCorte)
//                .request()
//                .delete();
//
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
//        }
//
//        ResponseInt reponseInt = response.readEntity(ResponseInt.class);
//        response.close();
//        client.close();
//        return reponseInt.getCodigoRespuesta();
//    }
    
    
    public static int agregarNota(NotaDatos notaDatos) {
        try {
            GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
            
            GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(Settings.ENDPOINTGRAPHQL)
                .requestMethod(GraphQLTemplate.GraphQLMethod.MUTATE)
                .request("mutation{createNotes(note: {"
                            + "codigoEstudianteAsignatura: " + notaDatos.getCodigoEstudianteAsignatura() + "," 
                            + "codigoCorte: " + notaDatos.getCodigoCorte() + "," 
                            + "nota: " + notaDatos.getNota() 
                    + "}){"
                            + "codigoRespuesta"
                            + "}"
                            + "}")
                .build();
            return graphQLTemplate.mutate(requestEntity, ResponseAgregarNota.class).getResponse().getCreateNotes().getCodigoRespuesta();

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
//    public static int agregarNota(NotaDatos notaDatos) {
//        Client client = ClientBuilder.newBuilder().build();
//        WebTarget target = client.target(Settings.APIRESTNOTAS + "/notas");
//        Response response = target
//                .request()
//                .post(Entity.entity(notaDatos, "application/json"));
//        System.out.println(response);
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
//        }
//
//        ResponseInt responseInt = response.readEntity(ResponseInt.class);
//        response.close();
//        client.close();
//        return responseInt.getCodigoRespuesta();
//    }
    
    public static boolean isNotaAlmacenada(int codigoEstudianteAsignatura, int codigoCorte) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/notas/almacenada");
        Response response = target
                .queryParam("codigoEstudianteAsignatura", codigoEstudianteAsignatura)
                .queryParam("codigoCorte", codigoCorte)
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        ResponseBoolean responseBoolean = response.readEntity(ResponseBoolean.class);
        response.close();
        client.close();
        return responseBoolean.isRespuesta();
    }
    
//    public static boolean isNotaAlmacenada(int codigoEstudianteAsignatura, int codigoCorte) {
//        Client client = ClientBuilder.newBuilder().build();
//        WebTarget target = client.target(Settings.APIRESTNOTAS + "/notas/almacenada");
//        Response response = target
//                .queryParam("codigoEstudianteAsignatura", codigoEstudianteAsignatura)
//                .queryParam("codigoCorte", codigoCorte)
//                .request()
//                .get();
//
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
//        }
//
//        ResponseBoolean responseBoolean = response.readEntity(ResponseBoolean.class);
//        response.close();
//        client.close();
//        return responseBoolean.isRespuesta();
//    }
    
    
    public static boolean isNotaPublicada(int codigoEstudianteAsignatura, int codigoCorte) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/notas/publicada");
        Response response = target
                .queryParam("codigoEstudianteAsignatura", codigoEstudianteAsignatura)
                .queryParam("codigoCorte", codigoCorte)
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        ResponseBoolean responseBoolean = response.readEntity(ResponseBoolean.class);
        response.close();
        client.close();
        return responseBoolean.isRespuesta();
    }
    
    public static int publicarNota(NotaDatos notaDatos) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/notas/publicar");
        Response response = target
                .request()
                .put(Entity.entity(notaDatos, "application/json"));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        ResponseInt responseInt = response.readEntity(ResponseInt.class);
        response.close();
        client.close();
        return responseInt.getCodigoRespuesta();
    }
    
    public static String getEmailEstudiante(int  codigoEstudianteAsignatura) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/estudiantes/email");
        Response response = target
                .queryParam("codigoEstudianteAsignatura", codigoEstudianteAsignatura)
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        ResponseString responseString = response.readEntity(ResponseString.class);
        response.close();
        client.close();
        return responseString.getRespuesta();
    }
    
    public static List<NotaEstudiante> getNotasEstudiante(int codigoEstudiante) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTAS + "/notas/notaestudiante");
        Response response = target
                .queryParam("codigoEstudiante", codigoEstudiante)
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        List<NotaEstudiante> notasEstudiante = response.readEntity(new GenericType<List<NotaEstudiante>>(){});
        response.close();
        client.close();
        return notasEstudiante;
    }
    
    public static String enviarEmail(Email email) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTIFICACIONES + "/notificaciones");
        Response response = target
                .request()
                .post(Entity.entity(email, "application/json"));
        
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        ResponseString responseString = response.readEntity(ResponseString.class);
        response.close();
        client.close();
        return responseString.getRespuesta();
    }
    
    public static List<Email> getEmails() {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(Settings.APIRESTNOTIFICACIONES + "/notificaciones");
        Response response = target
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " - Info: " + response.getStatusInfo());
        }

        List<Email> emails = response.readEntity(new GenericType<List<Email>>(){});
        response.close();
        client.close();
        return emails;
    }

    public static int crearAsistencia(int codigoEstudianteAsignatura, int codigoAsignatura) {
        try {
            GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
            
            GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(Settings.ENDPOINTGRAPHQL)
                .requestMethod(GraphQLTemplate.GraphQLMethod.MUTATE)
                .request("mutation {"
                            + "createAttendance(attendance: {" 
                            + "classCod: " + codigoAsignatura + ", "
                            + "studentCod: " + codigoEstudianteAsignatura + " "
                        + "}){"
                            + "id," 
                            + "classCod," 
                            + "checkDate" 
                            + "}" 
                        + "}")
                .build();
            return graphQLTemplate.mutate(requestEntity, ResponseCrearAsistencia.class).getResponse().getCreateAttendance().getId();

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static void eliminarAsistencia(int codigoAsistencia) {
        try {
            GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
            
            GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(Settings.ENDPOINTGRAPHQL)
                .requestMethod(GraphQLTemplate.GraphQLMethod.MUTATE)
                .request("mutation {"
                            + "deleteAttendance(id: " + codigoAsistencia + ")" 
                        + "}")
                .build();
            graphQLTemplate.mutate(requestEntity, ResponseEliminarAsistencia.class);

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
