package com.prueba.apirestful.servicio

import com.prueba.apirestful.Repository.TokenRepository
import com.prueba.apirestful.Service.TokenService
import spock.lang.Specification

class TokenServiceSpec extends Specification{
    private final TokenRepository repository = Mock(TokenRepository)

    private TokenService service = new TokenService(repository)
    String userId
    String jwt

    def setup(){
        userId = "8da58fde-488e-4426-88f8-0e13dbb5deae"
        jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NzkzNDA2NTQsImlhdCI6MTY3OTM0MDY0NCwianRpIjoiOGRhNThmZGUtNDg4ZS00NDI2LTg4ZjgtMGUxM2RiYjVkZWFlIn0.59DW_MmvRe1yx773pBuc0nZwa1ff_hCSGqjWfHGjz_8"
    }

    def 'Se evalua el correcto funcionamiento del servicio con datos correctos'(){
        when:'Se ejecuta el llamado al servicio registerUserToken que guardara la información enviada'
        def response = service.registerUserToken(userId,jwt)
        then:'Si la insercion es correcta la respuesta debe ser verdadero'
        true == response
    }

    def 'Provocar que funcionamiento de servicio tenga resultado incorrecto con string vacio'(){
        when:''
        def response = service.registerUserToken(userId,"")
        then:''
        false == response
    }

    def 'Provocar que funcionamiento de servicio tenga resultado incorrecto con datos nulo'(){
        when:'Se llama al servicio con dato nulo'
        def response = service.registerUserToken(null,null)
        then:'Respuesta debe ser falsa'
        false == response
    }
}
