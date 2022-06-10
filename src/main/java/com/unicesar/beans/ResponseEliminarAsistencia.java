
package com.unicesar.beans;

import java.sql.Timestamp;

/**
 *
 * @author orenaro
 */
public class ResponseEliminarAsistencia {
    
    private String deleteAttendance;

    public ResponseEliminarAsistencia() {
    }

    @Override
    public String toString() {
        return "ResponseEliminarAsistencia{" + "deleteAttendance=" + deleteAttendance + '}';
    }

    public String getDeleteAttendance() {
        return deleteAttendance;
    }

    public void setDeleteAttendance(String deleteAttendance) {
        this.deleteAttendance = deleteAttendance;
    }

    public ResponseEliminarAsistencia(String deleteAttendance) {
        this.deleteAttendance = deleteAttendance;
    }

}
