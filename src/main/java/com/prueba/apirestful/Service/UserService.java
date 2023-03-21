package com.prueba.apirestful.Service;

import com.prueba.apirestful.Config.ResourceProperties;
import com.prueba.apirestful.Entity.User;
import com.prueba.apirestful.Exception.ServiceException;
import com.prueba.apirestful.Repository.UserRepository;
import com.prueba.apirestful.Request.Response;
import com.prueba.apirestful.Util.Constants;
import com.prueba.apirestful.Util.JwtUtil;
import com.prueba.apirestful.Util.PasswordUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private ResourceProperties resourceProperties;
    private UserRepository userRepository;

    private IPhoneService phoneService;

    private ITokenService tokenService;

    private JwtUtil jwtUtil;

    private PasswordUtil passwordUtil;

    public UserService(UserRepository userRepository, IPhoneService phoneService, ITokenService tokenService, JwtUtil jwtUtil, PasswordUtil passwordUtil, ResourceProperties resourceProperties) {
        this.userRepository = userRepository;
        this.phoneService = phoneService;
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
        this.passwordUtil = passwordUtil;
        this.resourceProperties = resourceProperties;
    }

    @Override
    public Response registerUser(User user) {

        if (!resourceProperties.validFieldPassword(user.getPassword())) {
            throw new IllegalArgumentException(Constants.ERROR_PASSWORD);
        }
        if (!resourceProperties.validFieldEmail(user.getEmail())) {
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

            responseRequest = generateResponse(user, Uuid);

            boolean result2 = tokenService.registerUserToken(user.getUserId(), responseRequest.getToken());
            if (!result2) {
                throw new ServiceException(Constants.ERROR_TOKEN_SAVE);
            }

            return responseRequest;
        } catch (ServiceException ex) {
            throw new ServiceException(Constants.ERROR_INTERNO);
        }
    }

    private Response generateResponse(User user, String Uuid) {
        Response responseRequest = new Response();
        String tokenJwt = jwtUtil.generateTokenJwt(user);
        LocalDateTime date = LocalDateTime.now();
        responseRequest.setId(Uuid);
        responseRequest.setCreated(date);
        responseRequest.setModified(date);
        responseRequest.setLast_login(date);
        responseRequest.setToken(tokenJwt);
        responseRequest.setActive(true);

        return responseRequest;
    }

    private boolean existsByEmail(String email) {
        User user = userRepository.existsEmail(email);
        if (null == user) {
            return false;
        }
        return true;
    }

}
