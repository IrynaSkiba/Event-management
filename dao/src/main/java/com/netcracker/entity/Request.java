package com.netcracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "requests")
public class Request extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    private String comment;
    private RequestStatus status;

    public Request() {
    }

    public Request(int id, String comment, RequestStatus requestStatus) {
        super(id);
        this.comment = comment;
        this.status = requestStatus;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                ", \"comment\":\"" + comment + '\"' +
                ", \"client\":" + client.getId() +
                ", \"status\":\"" + status + '\"' +
                '}';
    }
}
