
package com.unicesar.beans;

import java.util.List;

/**
 *
 * @author orenaro
 */
public class ResponseGetEmails {
    
    private List<Email> getEmails;

    public ResponseGetEmails() {
    }

    public ResponseGetEmails(List<Email> getEmails) {
        this.getEmails = getEmails;
    }

    public List<Email> getGetEmails() {
        return getEmails;
    }

    public void setGetEmails(List<Email> getEmails) {
        this.getEmails = getEmails;
    }

    @Override
    public String toString() {
        return "ResponseGetEmails{" + "getEmails=" + getEmails + '}';
    }
    
    
}
