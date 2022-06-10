
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseAgregarNota {
    
    private CreateNotes createNotes;

    public ResponseAgregarNota() {
    }

    public ResponseAgregarNota(CreateNotes createNotes) {
        this.createNotes = createNotes;
    }

    public CreateNotes getCreateNotes() {
        return createNotes;
    }

    public void setCreateNotes(CreateNotes createNotes) {
        this.createNotes = createNotes;
    }

    @Override
    public String toString() {
        return "ResponseAgregarNota{" + "createNotes=" + createNotes + '}';
    }
    
    
    
    
    public class CreateNotes {
        private int codigoRespuesta;

        public CreateNotes() {
        }

        public CreateNotes(int codigoRespuesta) {
            this.codigoRespuesta = codigoRespuesta;
        }

        public int getCodigoRespuesta() {
            return codigoRespuesta;
        }

        public void setCodigoRespuesta(int codigoRespuesta) {
            this.codigoRespuesta = codigoRespuesta;
        }

        @Override
        public String toString() {
            return "createNotes{" + "codigoRespuesta=" + codigoRespuesta + '}';
        }

    }
}
