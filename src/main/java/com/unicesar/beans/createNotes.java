
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */

public class createNotes {
    private int createNotes;

    public createNotes() {
    }

    public createNotes(int createNotes) {
        this.createNotes = createNotes;
    }

    public int getCreateNotes() {
        return createNotes;
    }

    public void setCreateNotes(int createNotes) {
        this.createNotes = createNotes;
    }

    @Override
    public String toString() {
        return "createNotes{" + "createNotes=" + createNotes + '}';
    }
    
}
