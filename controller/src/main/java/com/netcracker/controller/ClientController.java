package com.netcracker.controller;

import com.netcracker.dto.*;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.exceptions.NotFoundException;
import com.netcracker.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("clients")
public class ClientController {
    private ClientServiceImpl clientService;
    private RequestServiceImpl requestService;
    private CommentServiceImpl commentService;
    private EventServiceImpl eventService;
    private UserServiceImpl userService;

    @Autowired
    public ClientController(ClientServiceImpl clientService, RequestServiceImpl requestService,
                            CommentServiceImpl commentService, EventServiceImpl eventService,
                            UserServiceImpl userService) {
        this.clientService = clientService;
        this.requestService = requestService;
        this.commentService = commentService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/{id}/profile")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientDto> getProfile(@PathVariable int id) {
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String username = getUsernameOfPrincipal();

        if (clientService.getByLogin(username).isPresent()) {
            if (clientService.getByLogin(username).get().getId() == id) {
                return new ResponseEntity<>(clientService.get(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientDto>> getClients() {
        return new ResponseEntity<>(clientService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientDto> getClient(@PathVariable int id) {
        try {
            return new ResponseEntity<>(clientService.get(id), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createClient(@RequestBody ClientDto clientDto) {
        try {
            if (!clientService.loginAlreadyExist(clientDto.getLogin())) {
                clientService.create(clientDto);
                return new ResponseEntity(HttpStatus.CREATED);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity updateClient(@RequestBody ClientDto clientDto) {
        String username = getUsernameOfPrincipal();

        if (clientService.getByLogin(username).isPresent()) {
            if (clientService.getByLogin(username).get().getId() == clientDto.getId()) {
                clientService.update(clientDto);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteClient(@PathVariable int id) {
        try {
            clientService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/discount")
    public ResponseEntity<Double> getDiscount(@PathVariable int id) {
        String username = getUsernameOfPrincipal();

        if (clientService.getByLogin(username).isPresent()) {
            if (clientService.getByLogin(username).get().getId() == id) {
                return new ResponseEntity<>(clientService.get(id).getDiscount(), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/requests/{request}")
    public ResponseEntity<RequestDto> getRequest(@PathVariable int id, @PathVariable int request) {
        String username = getUsernameOfPrincipal();
        try {
            if (clientService.getByLogin(username).isPresent()) {
                if (clientService.getByLogin(username).get().getId() == id) {
                    if (requestService.get(request).getClientId() == id) { //exception of service
                        return new ResponseEntity<>(requestService.get(request), HttpStatus.OK);
                    }
                } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/comments/{comment}")
    public ResponseEntity<CommentDto> getComment(@PathVariable int id, @PathVariable int comment) {
        String username = getUsernameOfPrincipal();
        try {
            if (clientService.getByLogin(username).isPresent()) {
                if (clientService.getByLogin(username).get().getId() == id) {
                    if (commentService.get(comment).getClientId() == id) {
                        return new ResponseEntity<>(commentService.get(comment), HttpStatus.OK);
                    }
                } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/events/{event}")
    public ResponseEntity<EventDto> getEvent(@PathVariable int id, @PathVariable int event) {
        String username = getUsernameOfPrincipal();
        try {
            if (clientService.getByLogin(username).isPresent()) {
                if (clientService.getByLogin(username).get().getId() == id) {
                    if (eventService.get(event).getClientId() == id) {
                        return new ResponseEntity<>(eventService.get(event), HttpStatus.OK);
                    }
                } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/requests")
    public ResponseEntity<List<RequestDto>> getRequests(@PathVariable int id) {
        String username = getUsernameOfPrincipal();
        try {
            if (userService.getByLogin(username).isPresent() &&
                    userService.getByLogin(username).get().getRole() == RoleDto.ADMIN) {
                return new ResponseEntity<>(clientService.get(id).getRequests().stream()
                        .map(x -> requestService.get(x))
                        .collect(Collectors.toList()), HttpStatus.OK);
            } else {
                if (clientService.getByLogin(username).isPresent()) {
                    if (clientService.getByLogin(username).get().getId() == id) {
                        return new ResponseEntity<>(clientService.get(id).getRequests().stream()
                                .map(x -> requestService.get(x))
                                .collect(Collectors.toList()), HttpStatus.OK);
                    } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable int id) {
        String username = getUsernameOfPrincipal();
        try {
            if (userService.getByLogin(username).isPresent() &&
                    userService.getByLogin(username).get().getRole() == RoleDto.ADMIN) {
                return new ResponseEntity<>(clientService.get(id).getComments().stream()
                        .map(x -> commentService.get(x))
                        .collect(Collectors.toList()), HttpStatus.OK);
            } else {
                if (clientService.getByLogin(username).isPresent()) {
                    if (clientService.getByLogin(username).get().getId() == id) {
                        return new ResponseEntity<>(clientService.get(id).getComments().stream()
                                .map(x -> commentService.get(x))
                                .collect(Collectors.toList()), HttpStatus.OK);
                    } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<List<EventDto>> getEvents(@PathVariable int id) {
        String username = getUsernameOfPrincipal();
        try {
            if (userService.getByLogin(username).isPresent() &&
                    userService.getByLogin(username).get().getRole() == RoleDto.ADMIN) {
                return new ResponseEntity<>(clientService.get(id).getEvents().stream()
                        .map(x -> eventService.get(x))
                        .collect(Collectors.toList()), HttpStatus.OK);
            } else {
                if (clientService.getByLogin(username).isPresent()) {
                    if (clientService.getByLogin(username).get().getId() == id) {
                        return new ResponseEntity<>(clientService.get(id).getEvents().stream()
                                .map(x -> eventService.get(x))
                                .collect(Collectors.toList()), HttpStatus.OK);
                    } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{id}/requests")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity createRequest(@PathVariable int id, @RequestBody RequestDto requestDto) {
        try {
            if (clientService.get(id) != null) {
                requestService.create(requestDto);
                return new ResponseEntity(HttpStatus.CREATED);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{id}/comments")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity createComment(@PathVariable int id, @RequestBody CommentDto commentDto) {
        try {
            if (clientService.get(id) != null) {
                commentService.create(commentDto);
                return new ResponseEntity(HttpStatus.CREATED);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}/requests")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity updateRequest(@PathVariable int id, @RequestBody RequestDto requestDto) {
        String username = getUsernameOfPrincipal();
        try {
            if (clientService.getByLogin(username).isPresent()) {
                if (clientService.getByLogin(username).get().getId() == id) {
                    if (clientService.get(id) != null &&
                            requestService.get(requestDto.getId()).getClientId() == requestDto.getClientId()) {
                        requestService.update(requestDto);
                        return new ResponseEntity(HttpStatus.OK);
                    } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}/comments")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity updateComment(@PathVariable int id, @RequestBody CommentDto commentDto) {
        String username = getUsernameOfPrincipal();
        try {
            if (clientService.getByLogin(username).isPresent()) {
                if (clientService.getByLogin(username).get().getId() == id) {
                    if (clientService.get(id) != null &&
                            commentService.get(commentDto.getId()).getClientId() == commentDto.getClientId()) {
                        commentService.update(commentDto);
                        return new ResponseEntity(HttpStatus.OK);
                    } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}/requests/{request}")
    public ResponseEntity deleteRequest(@PathVariable int id, @PathVariable int request) {
        String username = getUsernameOfPrincipal();
        try {
            if (userService.getByLogin(username).isPresent() &&
                    userService.getByLogin(username).get().getRole() == RoleDto.ADMIN) {
                requestService.delete(request);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                if (clientService.getByLogin(username).isPresent()) {
                    if (clientService.getByLogin(username).get().getId() == id) {
                        if (clientService.get(id) != null && requestService.get(request).getClientId() == request) {
                            requestService.delete(request);
                            return new ResponseEntity(HttpStatus.OK);
                        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}/comments/{comment}")
    public ResponseEntity deleteComment(@PathVariable int id, @PathVariable int comment) {
        String username = getUsernameOfPrincipal();
        try {
            if (userService.getByLogin(username).isPresent() &&
                    userService.getByLogin(username).get().getRole() == RoleDto.ADMIN) {
                requestService.delete(comment);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                if (clientService.getByLogin(username).isPresent()) {
                    if (clientService.getByLogin(username).get().getId() == id) {
                        if (clientService.get(id) != null && commentService.get(comment).getClientId() == id) {
                            commentService.delete(comment);
                            return new ResponseEntity(HttpStatus.OK);
                        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
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