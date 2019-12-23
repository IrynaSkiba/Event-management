package com.netcracker.controller;

import com.netcracker.dto.RequestDto;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.exceptions.NotFoundException;
import com.netcracker.service.impl.ClientServiceImpl;
import com.netcracker.service.impl.RequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("requests")
public class RequestController {
    private ClientServiceImpl clientService;
    private RequestServiceImpl requestService;

    @Autowired
    public RequestController(ClientServiceImpl clientService, RequestServiceImpl requestService) {
        this.clientService = clientService;
        this.requestService = requestService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity createRequest(@RequestBody RequestDto requestDto) {
        String username = getUsernameOfPrincipal();

        if (clientService.getByLogin(username).isPresent()) {
            if (requestDto.getClientId() == clientService.getByLogin(username).get().getId()) {
                requestService.create(requestDto);
                return new ResponseEntity(HttpStatus.CREATED);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RequestDto>> getRequests() {
        return new ResponseEntity<>(requestService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")//////////////////
    public ResponseEntity<RequestDto> getRequest(@PathVariable int id) {
        try {
            return new ResponseEntity<>(requestService.get(id), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteRequest(@PathVariable int id) {
        try {
            requestService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String getUsernameOfPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
