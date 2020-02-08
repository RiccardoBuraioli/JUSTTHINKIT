package controllers;

import java.util.logging.Logger;

import Dao.Services;

public class Login {

    private static final Logger log = Logger.getLogger(Login.class.getName());
    private final Services services;

    public Login(Services services) {
        this.services = services;
    }


    public boolean login(String email, String password) {
        if (services.checkEmail(email).equals("")) {
            log.warning("\t ***** ERROR: EMAIL IS NOT CORRECT *****");
            return false;
        } else {
            if (services.checkPassword(email, password)) {
                log.info("\t ***** LOGGED SUCCESSFULLY *****");
                return true;
            } else {
                log.warning("\t ***** ERROR: PASSWORD IS NOT CORRECT *****");
                return false;
            }
        }
    }


}


