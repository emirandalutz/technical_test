package com.prueba.apirestful.controller

import com.prueba.apirestful.Controller.UserController
import com.prueba.apirestful.Entity.User
import com.prueba.apirestful.Exception.ServiceException
import com.prueba.apirestful.Service.IUserService
import groovy.json.JsonSlurper
import spock.lang.Specification

class UserControllerSpec extends Specification{

    private IUserService service = Mock(IUserService)

    private UserController controller = new UserController(service)
    JsonSlurper slurper = new JsonSlurper()
    User user
    User user2

    def'Evaluar que respuesta sea 200 de exito'(){
        given:'Se entrega un body con datos correctos'
        user = slurper.parseText('{\n' +
                '   "name":"test prueba",\n' +
                '   "email": "testprueba@test.com",\n ' +
                '   "password": "TestPassword23",\n ' +
                '   "phones":   [   {' +
                '                   "number":"56489632",' +
                '                   "cityCode":"6",' +
                '                   "countryCode":"56"' +
                '                   }' +
                '               ]\n }') as User
        when:'se realiza el llamado al metodo registerUser'
        def response = controller.registerUser(user)
        then:'Se evalua la respuesta de exito'
        200 == response.getStatusCode().value()
    }

    def'Evaluar que respuesta lanze una excepcion al mandar un body incorrecto'(){
        given:'Entregamos un body con dato incorrecto'
        user2 = slurper.parseText('{\n' +
                '   "name":"",\n' +
                '   "email": "testprueba@test.com",\n ' +
                '   "password": "TestPassword23",\n ' +
                '   "phones":   [   {' +
                '                   "number":"56489632",' +
                '                   "cityCode":"6",' +
                '                   "countryCode":"56"' +
                '                   }' +
                '               ]\n }') as User
        and:'La llamada al servicio entrega un excepcion'
        service.registerUser(user2) >> {throw new ServiceException()}
        when:'Se llama el metodo registerUser'
        controller.registerUser(user2)
        then:'Se verifica que la excepcion sea un service excepcion'
        thrown(ServiceException.class)
    }


}
