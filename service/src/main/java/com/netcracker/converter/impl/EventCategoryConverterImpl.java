package com.netcracker.converter.impl;

import com.netcracker.converter.Converter;
import com.netcracker.dto.CategoryDto;
import com.netcracker.dto.EventCategoryDto;
import com.netcracker.entity.Category;
import com.netcracker.entity.EventCategory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

@Component
public class EventCategoryConverterImpl implements Converter<EventCategory, EventCategoryDto> {

    @Override
    public EventCategoryDto toDto(EventCategory entity){
        EventCategoryDto eventCategoryDto = new EventCategoryDto(entity.getId(),
                CategoryDto.valueOf(entity.getName().name()), entity.getPrice());
        return eventCategoryDto;
    }

    @Override
    public EventCategory toEntity(EventCategoryDto dto) {
        EventCategory eventCategory = new EventCategory(dto.getId(),
                Category.valueOf(dto.getName().name()), dto.getPrice());
        return eventCategory;
    }
}
