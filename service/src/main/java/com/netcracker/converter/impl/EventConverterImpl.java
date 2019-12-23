package com.netcracker.converter.impl;

import com.netcracker.converter.Converter;
import com.netcracker.dto.EventDto;
import com.netcracker.entity.BaseEntity;
import com.netcracker.entity.Event;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class EventConverterImpl implements Converter<Event, EventDto> {
    @Override
    public EventDto toDto(Event entity) {
        List<Integer> list = entity.getServices().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        EventDto eventDto = new EventDto(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getEventCategory().getId(), entity.getDate(), entity.getCost(),
                entity.getClient().getId(), entity.getEmployee().getId(), list);
        return eventDto;
    }

    @Override
    public Event toEntity(EventDto dto) {
        Event event = new Event(dto.getId(), dto.getName(), dto.getDescription(), dto.getDate(), dto.getCost());
        return event;
    }
}
