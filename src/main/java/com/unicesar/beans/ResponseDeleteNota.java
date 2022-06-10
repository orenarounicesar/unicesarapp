
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseDeleteNota {
    
    private DeleteNotes deleteNote;

    public ResponseDeleteNota() {
    }

    public ResponseDeleteNota(DeleteNotes createNotes) {
        this.deleteNote = createNotes;
    }

    public DeleteNotes getDeleteNote() {
        return deleteNote;
    }

    public void setDeleteNote(DeleteNotes deleteNotes) {
        this.deleteNote = deleteNotes;
    }

    @Override
    public String toString() {
        return "ResponseAgregarNota{" + "createNotes=" + deleteNote + '}';
    }
    
    
    
    
    public class DeleteNotes {
        private int codigoRespuesta;

        public DeleteNotes() {
        }

        public DeleteNotes(int codigoRespuesta) {
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
            return "deleteNotes{" + "codigoRespuesta=" + codigoRespuesta + '}';
        }

    }
}
