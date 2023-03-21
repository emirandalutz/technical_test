package com.prueba.apirestful.servicio

import com.prueba.apirestful.ApirestfulApplication
import com.prueba.apirestful.Config.ResourceProperties
import com.prueba.apirestful.Entity.Phone
import com.prueba.apirestful.Entity.User
import com.prueba.apirestful.Repository.UserRepository
import com.prueba.apirestful.Request.Response
import com.prueba.apirestful.Service.IPhoneService
import com.prueba.apirestful.Service.ITokenService
import com.prueba.apirestful.Service.UserService
import com.prueba.apirestful.Util.JwtUtil
import com.prueba.apirestful.Util.PasswordUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.LocalDateTime

@ContextConfiguration(classes = ApirestfulApplication.class)
@ActiveProfiles("test")
class UserServiceSpec extends Specification{

    private ResourceProperties properties = Mock(ResourceProperties)
    private UserRepository repository = Mock(UserRepository)
    private IPhoneService phoneService = Mock(IPhoneService)
    private ITokenService tokenService = Mock(ITokenService)
    private JwtUtil jwtUtil = Mock(JwtUtil)
    private PasswordUtil passwordUtil = Mock(PasswordUtil)

    private UserService service = new UserService(repository,phoneService,tokenService,jwtUtil,passwordUtil,properties)
    List<Phone> phones
    User user

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

    }

    Response response(){
        LocalDateTime date = LocalDateTime.now()
        Response response = new Response()
        response.setId("test-id")
        response.setCreated(date)
        response.setLast_login(date)
        response.setModified(date)
        response.setToken("test-token")
        response.setActive(true)

        return response
    }
}
