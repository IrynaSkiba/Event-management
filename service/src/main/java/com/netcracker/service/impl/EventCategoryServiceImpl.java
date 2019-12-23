package com.netcracker.service.impl;

import com.netcracker.converter.impl.EventCategoryConverterImpl;
import com.netcracker.dao.repository.EventCategoryRepository;
import com.netcracker.dto.EventCategoryDto;
import com.netcracker.entity.EventCategory;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.exceptions.NotFoundException;
import com.netcracker.service.CrudService;
import com.netcracker.service.ValidId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventCategoryServiceImpl implements CrudService<EventCategoryDto>, ValidId {
    private EventCategoryRepository eventCategoryRepository;
    private EventCategoryConverterImpl converter;

    @Autowired
    public EventCategoryServiceImpl(EventCategoryRepository eventCategoryRepository, EventCategoryConverterImpl converter) {
        this.eventCategoryRepository = eventCategoryRepository;
        this.converter = converter;
    }

    @Override
    public void create(EventCategoryDto eventCategoryDto) {
            EventCategory eventCategory = converter.toEntity(eventCategoryDto);
            eventCategoryRepository.save(eventCategory);
    }

    @Override
    public EventCategoryDto get(int id) {
        EventCategoryDto eventCategoryDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (eventCategoryRepository.findById(id).isPresent()) {
            EventCategory eventCategory = eventCategoryRepository.findById(id).get();
            eventCategoryDto = converter.toDto(eventCategory);
        } else {
            throw new NotFoundException("Category not found");
        }
        return eventCategoryDto;
    }

    @Override
    public List<EventCategoryDto> getAll() {
        List<EventCategoryDto> list = eventCategoryRepository.findAll().stream()
                .map(x -> converter.toDto(x))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public void update(EventCategoryDto eventCategoryDto) {
        if (eventCategoryDto.getId() <= 0) throw new BadRequestException("Not valid data");
        if (eventCategoryRepository.findById(eventCategoryDto.getId()).isPresent()) {
            eventCategoryRepository.delete(eventCategoryRepository.findById(eventCategoryDto.getId()).get());
            eventCategoryRepository.save(converter.toEntity(eventCategoryDto));
        } else {
            throw new NotFoundException("Category doesn't exist");
        }
    }

    @Override
    public void delete(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (eventCategoryRepository.findById(id).isPresent()) {
            eventCategoryRepository.delete(eventCategoryRepository.findById(id).get());
        } else {
            throw new NotFoundException("Category doesn't exist");
        }
    }

    @Override
    public boolean idAlreadyExist(int id) {
        return eventCategoryRepository.findAll().stream().anyMatch(l -> l.getId() == id);
    }
}
