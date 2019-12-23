package com.netcracker.controller;

import com.netcracker.dto.EventCategoryDto;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.exceptions.NotFoundException;
import com.netcracker.service.impl.EventCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event_categories")
public class EventCategoryController {
    private EventCategoryServiceImpl categoryService;

    @Autowired
    public EventCategoryController(EventCategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public ResponseEntity<List<EventCategoryDto>> getEventCategories() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public ResponseEntity<EventCategoryDto> getEventCategory(@PathVariable int id) {
        try {
            return new ResponseEntity<>(categoryService.get(id), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createEventCategory(@RequestBody EventCategoryDto eventCategoryDto) {
            categoryService.create(eventCategoryDto);
            return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateEventCategory(@RequestBody EventCategoryDto eventCategoryDto) {
        try {
            categoryService.update(eventCategoryDto);
            return new ResponseEntity(HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteEventCategory(@PathVariable int id) {
        try {
            categoryService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
