package com.netcracker.service.impl;

import com.netcracker.converter.impl.RequestConverterImpl;
import com.netcracker.dao.repository.ClientRepository;
import com.netcracker.dao.repository.RequestRepository;
import com.netcracker.dto.RequestDto;
import com.netcracker.entity.Request;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.exceptions.NotFoundException;
import com.netcracker.service.CrudService;
import com.netcracker.service.ValidId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements CrudService<RequestDto>, ValidId {
    private RequestRepository requestRepository;
    private RequestConverterImpl converter;
    private ClientRepository clientRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, RequestConverterImpl converter,
                              ClientRepository clientRepository) {
        this.requestRepository = requestRepository;
        this.converter = converter;
        this.clientRepository = clientRepository;
    }

    @Override
    public void create(RequestDto requestDto) {
        Request request = converter.toEntity(requestDto);
        request.setClient(clientRepository.findById(requestDto.getClientId()).get());
        requestRepository.save(request);
    }

    @Override
    public RequestDto get(int id) {
        RequestDto requestDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (requestRepository.findById(id).isPresent()) {
            Request request = requestRepository.findById(id).get();
            requestDto = converter.toDto(request);
        } else {
            throw new NotFoundException("Request not found");
        }
        return requestDto;
    }

    @Override
    public List<RequestDto> getAll() {
        List<RequestDto> list = requestRepository.findAll().stream()
                .map(x -> converter.toDto(x))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public void update(RequestDto requestDto) {
        if (requestRepository.findById(requestDto.getId()).isPresent()) {
            requestRepository.delete(requestRepository.findById(requestDto.getId()).get());
            requestRepository.save(converter.toEntity(requestDto));
        }
    }

    @Override
    public void delete(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (requestRepository.findById(id).isPresent()) {
            requestRepository.delete(requestRepository.findById(id).get());
        } else {
            throw new NotFoundException("Request doesn't exist");
        }
    }

    @Override
    public boolean idAlreadyExist(int id) {
        return requestRepository.findAll().stream().anyMatch(l -> l.getId() == id);
    }
}
