package com.netcracker.controller;

import com.netcracker.dto.ClientDto;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    private ClientServiceImpl clientService;

    @Autowired
    public RegistrationController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity registrationClient( @RequestBody ClientDto clientDto) {
        try {
            if (!clientService.loginAlreadyExist(clientDto.getLogin())) {
                clientService.create(clientDto);
                return new ResponseEntity(HttpStatus.CREATED);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
