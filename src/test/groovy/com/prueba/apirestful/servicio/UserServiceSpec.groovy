package com.prueba.apirestful.servicio

import com.prueba.apirestful.Config.ResourceProperties
import com.prueba.apirestful.Entity.Phone
import com.prueba.apirestful.Entity.User
import com.prueba.apirestful.Exception.ServiceException
import com.prueba.apirestful.Repository.UserRepository
import com.prueba.apirestful.Request.Response
import com.prueba.apirestful.Service.IPhoneService
import com.prueba.apirestful.Service.ITokenService
import com.prueba.apirestful.Service.UserService
import com.prueba.apirestful.Util.JwtUtil
import com.prueba.apirestful.Util.PasswordUtil
import spock.lang.Specification

import java.time.LocalDateTime

class UserServiceSpec extends Specification{

    private ResourceProperties properties = Mock(ResourceProperties)

    private UserRepository repository = Mock(UserRepository)
    private IPhoneService phoneService = Mock(IPhoneService)
    private ITokenService tokenService = Mock(ITokenService)
    private JwtUtil jwtUtil = Mock(JwtUtil)
    private PasswordUtil passwordUtil = Stub(PasswordUtil)

    private UserService service = new UserService(repository,phoneService,tokenService,jwtUtil,passwordUtil,properties)
    List<Phone> phones
    User user
    String passwordEncode
    String token

    def setup(){
        user = new User()
        user.setName("test_1")
        user.setEmail("test@test.com")
        user.setPassword("technicalTest123")

        Phone phone1 = new Phone()
        phone1.setCityCode("56")
        phone1.setCountryCode("453")
        phone1.setNumber("54345678")
        Phone phone2 = new Phone()
        phone2.setCityCode("5")
        phone2.setCountryCode("56")
        phone2.setNumber("98767896")

        phones = new ArrayList<Phone>()
        phones.addAll([phone1,phone2])

        user.setPhones(phones)
        passwordEncode = "yAfaQMWlkUBWuv2vKi9NeeEBgtIlYVXwJNFiaCZ6OrVQmJgRD//OW"
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk0MTk2MDcsImlhdCI6MTY3OTQxOTU5NywianRpIjoiMGFhODQ0NzQtNmYzYy00ZmI1LTg4MmUtMGQ0ZGQ1Y2I3M2VjIn0.Vil2BGfwRt4cpSCnESYJZ3ii3C9fkxsZviIvwjggvbw"
    }

    def 'Prueba para evaluar el funcionamiento correcto del servicio'(){
        given:'Se entregan todos los mock y respuestas a los servicio y clases utilitarias'
        properties.validFieldPassword(_ as String) >> true
        properties.validFieldEmail(_ as String) >> true
        passwordUtil.passwordEncoder().encode(_ as String) >> passwordEncode
        jwtUtil.generateTokenJwt(_ as User) >> token
        phoneService.registerPhones(_ as User, _ as List<Phone>) >> true
        tokenService.registerUserToken(_ as String, _ as String) >> true
        when: 'Se ejecuta el llamado del servicio registerUser con datos validos'
        def response = service.registerUser(user)
        then:'Se evalua que la respuesta contenga todos los datos solicitados'
        null != response.id || false == response.id.isEmpty()
        null != response.created
        null != response.modified
        null != response.last_login
        null != response.token || false == response.token.isEmpty()
        true == response.active || false == response.active

    }

    def 'Prueba para gatillar error en procesos de validación de password'(){
        given:'Se entrega respuesta negativa al validar password'
        properties.validFieldPassword(_ as String) >> false
        when: 'Se ejecuta el llamado del servicio registerUser'
        service.registerUser(user)
        then:'Se evalua que se lance la excepcion'
        thrown(IllegalArgumentException.class)
    }

    def 'Prueba para gatillar error en procesos de validación de email'(){
        given:'Se entrega respuesta positiva al validar password'
        properties.validFieldPassword(_ as String) >> true
        and:'Se entrega respuesta negativa al validar email'
        properties.validFieldEmail(_ as String) >> false
        when: 'Se ejecuta el llamado del servicio registerUser'
        service.registerUser(user)
        then:'Se evalua que se lance la excepcion'
        thrown(IllegalArgumentException.class)
    }

    def 'Prueba para evaluar error al llamar al metodo registerPhones'(){
        given:'Se entrega respuesta positiva al validar password'
        properties.validFieldPassword(_ as String) >> true
        and:'Se entrega respuesta positiva al validar email'
        properties.validFieldEmail(_ as String) >> true
        and:'Se entrega respuesta valida al encryptar password'
        passwordUtil.passwordEncoder().encode(_ as String) >> passwordEncode
        and:'Se entrega respuesta valida al generar token'
        jwtUtil.generateTokenJwt(_ as User) >> token
        and:'Respuesta falsa al registrar telefonos'
        phoneService.registerPhones(_ as User, _ as List<Phone>) >> false
        when: 'Se ejecuta el llamado del servicio registerUser con datos validos'
        service.registerUser(user)
        then:'Se evalua que se lance la excepcion'
        thrown(ServiceException.class)

    }

    def 'Prueba para evaluar error al llamar al metodo registerUserToken'(){
        given:'Se entrega respuesta positiva al validar password'
        properties.validFieldPassword(_ as String) >> true
        and:'Se entrega respuesta positiva al validar email'
        properties.validFieldEmail(_ as String) >> true
        and:'Se entrega respuesta valida al encryptar password'
        passwordUtil.passwordEncoder().encode(_ as String) >> passwordEncode
        and:'Se entrega respuesta valida al generar token'
        jwtUtil.generateTokenJwt(_ as User) >> token
        and:'Respuesta verdadera al registrar telefonos'
        phoneService.registerPhones(_ as User, _ as List<Phone>) >> true
        and:'Respuesta falsa al registrar token'
        tokenService.registerUserToken(_ as String, _ as String) >> false
        when: 'Se ejecuta el llamado del servicio registerUser con datos validos'
        service.registerUser(user)
        then:'Se evalua que se lance la excepcion'
        thrown(ServiceException.class)

    }

}
