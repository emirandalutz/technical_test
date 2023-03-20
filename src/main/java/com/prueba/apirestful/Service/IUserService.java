package com.prueba.apirestful.Service;

import com.prueba.apirestful.Entity.User;
import com.prueba.apirestful.Exception.ServiceException;
import com.prueba.apirestful.Request.Response;

public interface IUserService {

    Response registerUser(User user) throws ServiceException;

}
