package com.netcracker.converter.impl;

import com.netcracker.converter.Converter;
import com.netcracker.dto.RequestDto;
import com.netcracker.dto.RequestStatusDto;
import com.netcracker.entity.Request;
import com.netcracker.entity.RequestStatus;
import org.springframework.stereotype.Component;

@Component
public class RequestConverterImpl implements Converter<Request, RequestDto> {
    @Override
    public RequestDto toDto(Request entity) {
        RequestDto requestDto = new RequestDto(entity.getId(), entity.getClient().getId(),
                entity.getComment(), RequestStatusDto.valueOf(entity.getStatus().name()));
        return requestDto;
    }

    @Override
    public Request toEntity(RequestDto dto) {
        Request request = new Request(dto.getId(), dto.getComment(), RequestStatus.valueOf(dto.getStatus().name()));
        return request;
    }
}
