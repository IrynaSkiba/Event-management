package com.netcracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
    private int rating;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    public Comment() {
    }

    public Comment(int id, int rating, String content) {
        super(id);
        this.rating = rating;
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"rating\":" + rating +
                ", \"content\":\"" + content + '\"' +
                ", \"client\":" + client.getId() +
                ", \"service\":\"" + service.getId() +
                '}';
    }
}
