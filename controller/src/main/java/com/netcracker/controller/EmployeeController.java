package com.netcracker.controller;

import com.netcracker.dto.EmployeeDto;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.exceptions.NotFoundException;
import com.netcracker.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}/profile")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<EmployeeDto> getProfile(@PathVariable int id) {
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String username = getUsernameOfPrincipal();

        if (employeeService.getByLogin(username).isPresent()) {
            if (employeeService.getByLogin(username).get().getId() == id) {
                return new ResponseEntity<>(employeeService.get(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable int id) {
        try {
            return new ResponseEntity<>(employeeService.get(id), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            if (!employeeService.loginAlreadyExist(employeeDto.getLogin())) {
                employeeService.create(employeeDto);
                return new ResponseEntity(HttpStatus.CREATED);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateEmployeeOfAdmin(@RequestBody EmployeeDto employeeDto) {
        try {
            employeeService.update(employeeDto);
            return new ResponseEntity(HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteEmployee(@PathVariable int id) {
        try {
            employeeService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
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
