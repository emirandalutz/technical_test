package com.prueba.apirestful.controller

import com.prueba.apirestful.Controller.UserController
import com.prueba.apirestful.Service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@WebMvcTest(controllers = [UserController])
class UserControllerSpec extends Specification{

    @Autowired
    protected MockMvc mvc
    @Autowired
    private IUserService service

    private UserController controller = new UserController(service)

}
