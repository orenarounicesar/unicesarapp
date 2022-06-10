
package com.unicesar.utils;

import com.unicesar.beans.Email;

public class EnviarEmail extends Thread {
    
    private final Email email;

    public EnviarEmail(Email email) {
        this.email = email;
    }
    
    @Override
    public void run() {
        Enrutador.enviarEmail(email);
    }
    
}
