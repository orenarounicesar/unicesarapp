
package com.unicesar.beans;

import java.sql.Timestamp;

/**
 *
 * @author orenaro
 */
public class ResponseCrearAsistencia {
    
    private CreateAttendance createAttendance;

    public ResponseCrearAsistencia() {
    }

    public ResponseCrearAsistencia(CreateAttendance createAttendance) {
        this.createAttendance = createAttendance;
    }

    public CreateAttendance getCreateAttendance() {
        return createAttendance;
    }

    public void setCreateAttendance(CreateAttendance createAttendance) {
        this.createAttendance = createAttendance;
    }

    @Override
    public String toString() {
        return "ResponseCrearAsistencia{" + "createAttendance=" + createAttendance + '}';
    }
    
    
    
    
    public class CreateAttendance {
        private int id;
        private int classCod;
        private Timestamp checkDate;

        public CreateAttendance() {
        }

        public CreateAttendance(int id, int classCod, Timestamp checkDate) {
            this.id = id;
            this.classCod = classCod;
            this.checkDate = checkDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getClassCod() {
            return classCod;
        }

        public void setClassCod(int classCod) {
            this.classCod = classCod;
        }

        public Timestamp getCheckDate() {
            return checkDate;
        }

        public void setCheckDate(Timestamp checkDate) {
            this.checkDate = checkDate;
        }

        @Override
        public String toString() {
            return "createAttendance{" + "id=" + id + ", classCod=" + classCod + ", checkDate=" + checkDate + '}';
        }
        
    }
}
