package com.prueba.apirestful.Service;

import com.prueba.apirestful.Entity.Token;
import com.prueba.apirestful.Exception.ServiceException;
import com.prueba.apirestful.Repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService implements ITokenService{

    private TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository){
        this.tokenRepository = tokenRepository;
    }

    @Override
    public boolean registerUserToken(String uuid, String tokenJwt) {
        Token token = new Token();
        token.setUuid(uuid);
        token.setJwtToken(tokenJwt);

        try{
            tokenRepository.save(token);
            return true;
        } catch (ServiceException ex) {
            return false;
        }
    }
}
