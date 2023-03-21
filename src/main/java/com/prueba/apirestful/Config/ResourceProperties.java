package com.prueba.apirestful.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResourceProperties {
    @Value("${email.regexp}")
    private String emailRegexp;

    @Value("${password.regexp}")
    private String passwordRegexp;

    public String getEmailRegexp() {
        return emailRegexp;
    }

    public String getPasswordRegexp() {
        return passwordRegexp;
    }
}
