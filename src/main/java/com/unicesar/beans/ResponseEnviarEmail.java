
package com.unicesar.beans;

/**
 *
 * @author orenaro
 */
public class ResponseEnviarEmail {
    
    private ResponseString enviarEmail;

    public ResponseEnviarEmail() {
    }

    public ResponseEnviarEmail(ResponseString enviarEmail) {
        this.enviarEmail = enviarEmail;
    }

    public ResponseString getEnviarEmail() {
        return enviarEmail;
    }

    public void setEnviarEmail(ResponseString enviarEmail) {
        this.enviarEmail = enviarEmail;
    }

    @Override
    public String toString() {
        return "ResponseEnviarEmail{" + "enviarEmail=" + enviarEmail + '}';
    }
    
}
