package com.netcracker.service.impl;

import com.netcracker.converter.impl.CommentConverterImpl;
import com.netcracker.dao.repository.ClientRepository;
import com.netcracker.dao.repository.CommentRepository;
import com.netcracker.dao.repository.ServiceRepository;
import com.netcracker.dto.CommentDto;
import com.netcracker.entity.Comment;
import com.netcracker.service.CommentService;
import com.netcracker.service.CrudService;
import com.netcracker.service.ValidId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CrudService<CommentDto>, ValidId, CommentService {
    private CommentRepository commentRepository;
    private CommentConverterImpl converter;
    private ClientRepository clientRepository;
    private ServiceRepository serviceRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentConverterImpl converter,
                              ClientRepository clientRepository, ServiceRepository serviceRepository) {
        this.commentRepository = commentRepository;
        this.converter = converter;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void create(CommentDto commentDto) {
        Comment comment = converter.toEntity(commentDto);
        if (clientRepository.findById(commentDto.getClientId()).isPresent()
                && serviceRepository.findById(commentDto.getServiceId()).isPresent()) {
            comment.setClient(clientRepository.findById(commentDto.getClientId()).get());
            comment.setService(serviceRepository.findById(commentDto.getServiceId()).get());
            commentRepository.save(comment);
        }
    }

    @Override
    public CommentDto get(int id) {
        CommentDto commentDto = null;
        if (commentRepository.findById(id).isPresent()) {
            Comment comment = commentRepository.findById(id).get();
            commentDto = converter.toDto(comment);
        }
        return commentDto;
    }

    @Override
    public List<CommentDto> getAll() {
        List<CommentDto> list = commentRepository.findAll().stream()
                .map(x -> converter.toDto(x))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public void update(CommentDto commentDto) {
        if (commentRepository.findById(commentDto.getId()).isPresent()) {
            commentRepository.delete(commentRepository.findById(commentDto.getId()).get());
            commentRepository.save(converter.toEntity(commentDto));
        }
    }

    @Override
    public void delete(int id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.delete(commentRepository.findById(id).get());
        }
    }

    @Override
    public boolean commentAlreadyExist(int client, int service) {
        return commentRepository.findAll().stream()
                .anyMatch(x -> x.getClient().getId() == client && x.getService().getId() == service);
    }

    @Override
    public boolean idAlreadyExist(int id) {
        return commentRepository.findAll().stream().anyMatch(l -> l.getId() == id);
    }
}
