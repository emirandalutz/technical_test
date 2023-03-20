package com.prueba.apirestful.Service;

import com.prueba.apirestful.Entity.Phone;
import com.prueba.apirestful.Entity.User;
import com.prueba.apirestful.Exception.ServiceException;

import java.util.List;

public interface IPhoneService {
    boolean registerPhones(User userId, List<Phone> phoneList) throws ServiceException;
}
