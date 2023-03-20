package com.prueba.apirestful.Service;

import com.prueba.apirestful.Entity.User;
import com.prueba.apirestful.Exception.ServiceException;
import com.prueba.apirestful.Repository.UserRepository;
import com.prueba.apirestful.Request.Response;
import com.prueba.apirestful.Util.Constants;
import com.prueba.apirestful.Util.JwtUtil;
import com.prueba.apirestful.Util.PasswordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements IUserService {
    @Value("${email.regexp}")
    private String emailRegexp;

    @Value("${password.regexp}")
    private String passwordRegexp;

    private UserRepository userRepository;

    private IPhoneService phoneService;

    private ITokenService tokenService;

    private JwtUtil jwtUtil;

    private PasswordUtil passwordUtil;

    public UserService(UserRepository userRepository, IPhoneService phoneService, ITokenService tokenService, JwtUtil jwtUtil, PasswordUtil passwordUtil) {
        this.userRepository = userRepository;
        this.phoneService = phoneService;
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public Response registerUser(User user) {

        if (!validField(passwordRegexp,user.getPassword())) {
            throw new IllegalArgumentException(Constants.ERROR_PASSWORD);
        }
        if (!validField(emailRegexp, user.getEmail())) {
            throw new IllegalArgumentException(Constants.ERROR_EMAIL);
        }
        if (existsByEmail(user.getEmail())) {
            throw new DuplicateKeyException(Constants.ERROR_EMAIL_EXISTE);
        }
        try {
            String Uuid = UUID.randomUUID().toString();
            user.setUserId(Uuid);
            Response responseRequest = new Response();
            String passwordEncode = passwordUtil.passwordEncoder().encode(user.getPassword());
            user.setPassword(passwordEncode);
            userRepository.save(user);

            boolean result = phoneService.registerPhones(user, user.getPhones());
            if (!result) {
                throw new ServiceException(Constants.ERROR_PHONE_SAVE);
            }

            LocalDateTime date = LocalDateTime.now();
            responseRequest.setId(Uuid);
            responseRequest.setCreated(date);
            responseRequest.setModified(date);
            responseRequest.setLast_login(date);
            responseRequest.setToken(jwtUtil.generateTokenJwt(user));
            responseRequest.setActive(true);

            boolean result2 = tokenService.registerUserToken(Uuid,responseRequest.getToken());
            if (!result2) {
                throw new ServiceException(Constants.ERROR_TOKEN_SAVE);
            }

            return responseRequest;
        } catch (ServiceException ex) {
            throw new ServiceException(Constants.ERROR_INTERNO);
        }
    }
    private boolean validField(String regexp, String input) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(input);

        boolean resp = matcher.matches();
        return resp;
    }

    private boolean existsByEmail(String email) {
        User user = userRepository.existsEmail(email);
        if (null == user) {
            return false;
        }
        return true;
    }

}
