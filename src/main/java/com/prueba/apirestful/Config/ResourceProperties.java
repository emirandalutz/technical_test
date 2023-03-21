package com.prueba.apirestful.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ResourceProperties {
    @Value("${email.regexp}")
    private String emailRegexp;

    @Value("${password.regexp}")
    private String passwordRegexp;

    public boolean validFieldPassword(String password) {

        Pattern pattern = Pattern.compile(passwordRegexp);
        Matcher matcher = pattern.matcher(password);

        boolean resp = matcher.matches();
        return resp;
    }

    public boolean validFieldEmail(String email) {

        Pattern pattern = Pattern.compile(emailRegexp);
        Matcher matcher = pattern.matcher(email);

        boolean resp = matcher.matches();
        return resp;
    }
}
