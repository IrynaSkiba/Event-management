package com.netcracker.service.impl;

import com.netcracker.converter.impl.ClientConverterImpl;
import com.netcracker.dao.repository.ClientRepository;
import com.netcracker.dao.repository.CommentRepository;
import com.netcracker.dao.repository.EventRepository;
import com.netcracker.dao.repository.RequestRepository;
import com.netcracker.dto.ClientDto;
import com.netcracker.dto.RoleDto;
import com.netcracker.entity.Client;
import com.netcracker.entity.Comment;
import com.netcracker.entity.Event;
import com.netcracker.entity.Request;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.exceptions.NotFoundException;
import com.netcracker.service.ClientService;
import com.netcracker.service.CrudService;
import com.netcracker.service.ValidId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements CrudService<ClientDto>, ValidId, ClientService {
    private ClientRepository clientRepository;
    private ClientConverterImpl converter;
    private EventRepository eventRepository;
    private CommentRepository commentRepository;
    private RequestRepository requestRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientConverterImpl converter,
                             EventRepository eventRepository, CommentRepository commentRepository,
                             RequestRepository requestRepository, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.converter = converter;
        this.eventRepository = eventRepository;
        this.commentRepository = commentRepository;
        this.requestRepository = requestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(ClientDto clientDto) {
        if (clientDto.getPhone() < 0 || clientDto.getTelegram() < 0 || clientDto.getDiscount() < 0)
            throw new BadRequestException("Not valid data");
        try {
            List<Event> events = clientDto.getEvents().stream()
                    .map(a -> eventRepository.findById(a).get())
                    .collect(Collectors.toList());

            List<Comment> comments = clientDto.getComments().stream()
                    .map(x -> commentRepository.findById(x).get())
                    .collect(Collectors.toList());

            List<Request> requests = clientDto.getRequests().stream()
                    .map(x -> requestRepository.findById(x).get())
                    .collect(Collectors.toList());

            clientDto.setPassword(passwordEncoder.encode(clientDto.getPassword()));

            Client client = converter.toEntity(clientDto);
            client.setEvents(events);
            client.setComments(comments);
            client.setRequests(requests);
            clientRepository.save(client);
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public ClientDto get(int id) {
        ClientDto clientDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (clientRepository.findById(id).isPresent()) {
            Client client = clientRepository.findById(id).get();
            clientDto = converter.toDto(client);
        } else {
            throw new NotFoundException("Client not found");
        }
        return clientDto;
    }

    @Override
    public List<ClientDto> getAll() {
        List<ClientDto> list = clientRepository.findAll().stream()
                .map(x -> converter.toDto(x))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public void update(ClientDto clientDto) {
        if (clientDto.getId() <= 0 || clientDto.getRole() != RoleDto.CLIENT)
            throw new BadRequestException("Not valid data");
        if (clientRepository.findById(clientDto.getId()).isPresent()) {
            clientRepository.delete(clientRepository.findById(clientDto.getId()).get());
            clientRepository.save(converter.toEntity(clientDto));
        } else {
            throw new NotFoundException("Client doesn't exist");
        }
    }

    @Override
    public void delete(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (clientRepository.findById(id).isPresent()) {
            clientRepository.delete(clientRepository.findById(id).get());
        } else {
            throw new NotFoundException("Client doesn't exist");
        }
    }

    @Override
    public boolean idAlreadyExist(int id) {
        return clientRepository.findAll().stream().anyMatch(l -> l.getId() == id);
    }

    @Override
    public Optional<ClientDto> getByLogin(String login) {
        Optional<Client> client = clientRepository.findAll().stream()
                .filter(x -> x.getLogin().equals(login))
                .findFirst();
        ClientDto clientDto = null;
        if (client.isPresent()) clientDto = converter.toDto(client.get());
        return Optional.ofNullable(clientDto);
    }

    @Override
    public boolean loginAlreadyExist(String login) {
        return clientRepository.findAll().stream().anyMatch(l -> l.getLogin().equals(login));
    }
}
