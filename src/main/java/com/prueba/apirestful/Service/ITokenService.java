package com.prueba.apirestful.Service;

import com.prueba.apirestful.Exception.ServiceException;

public interface ITokenService {
    boolean registerUserToken(String uuid, String token) throws ServiceException;
}
