package com.prueba.apirestful.Service;

import com.prueba.apirestful.Entity.Phone;
import com.prueba.apirestful.Entity.User;
import com.prueba.apirestful.Repository.PhoneRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService implements IPhoneService {

    private PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public boolean registerPhones(User userId, List<Phone> phoneList) {
        if(null != userId && null !=phoneList) {
            phoneList.stream().forEach(phone -> {
                Phone phoneRelation = new Phone();
                BeanUtils.copyProperties(phone, phoneRelation);
                phoneRelation.setUserId(userId);
                phoneRepository.save(phoneRelation);
            });
            return true;
        } else {
            return false;
        }
    }


}
