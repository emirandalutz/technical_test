package com.prueba.apirestful.Controller;

import com.prueba.apirestful.Exception.ServiceException;
import com.prueba.apirestful.Request.Response;
import com.prueba.apirestful.Entity.User;
import com.prueba.apirestful.Service.IUserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class UserController {
    private IUserService userService;

    public UserController(IUserService userService){
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> registerUser(@Valid @RequestBody User user) throws ServiceException {
        Response response = null;
        try {
            response = userService.registerUser(user);
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }
}
