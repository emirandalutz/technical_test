package com.prueba.apirestful.service

import com.prueba.apirestful.Entity.Phone
import com.prueba.apirestful.Entity.User
import com.prueba.apirestful.Repository.PhoneRepository
import com.prueba.apirestful.Service.PhoneService
import spock.lang.Specification

class PhoneServiceSpec extends Specification {

    private final PhoneRepository repository = Mock(PhoneRepository)

    private PhoneService service = new PhoneService(repository)
    List<Phone> phones;
    User user1 = Mock(User)

    def setup() {
        Phone phone1 = new Phone()
        phone1.setCityCode("56")
        phone1.setCountryCode("453")
        phone1.setNumber("54345678")
        phone1.setUserId(user1)
        Phone phone2 = new Phone()
        phone2.setCityCode("5")
        phone2.setCountryCode("56")
        phone2.setNumber("98767896")
        phone2.setUserId(user1)

        phones = new ArrayList<Phone>()
        phones.addAll([phone1, phone2])
    }

    def 'Se evalua el correcto funcionamiento del servicio con datos correctos'() {
        when: 'Se ejecuta el llamado al servicio registerPhones que guardara la información enviada'
        def response = service.registerPhones(user1, phones)
        then: 'Si la insercion es correcta la respuesta debe ser verdadero'
        true == response

    }

    def 'Provocar que funcionamiento de servicio tenga resultado incorrecto'(){
        when:'se ejecuta la llamada al servicio con dato incorrecto'
        def response = service.registerPhones(null, phones)
        then:'Respuesta debe ser falsa'
        false == response
    }


}
