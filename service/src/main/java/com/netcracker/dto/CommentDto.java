package com.netcracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto extends BaseDto {
    private int rating;
    private String content;
    private int clientId;
    private int serviceId;

    public CommentDto() {
    }

    public CommentDto(int id, int rating, String content, int clientId, int serviceId) {
        super(id);
        this.rating = rating;
        this.content = content;
        this.clientId = clientId;
        this.serviceId = serviceId;
    }

    public CommentDto(String content, int clientId, int serviceId) {
        this.content = content;
        this.clientId = clientId;
        this.serviceId = serviceId;
    }

    public CommentDto(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"rating\":" + rating +
                ", \"content\":\"" + content + '\"' +
                ", \"client\":" + clientId +
                ", \"service\":\"" + serviceId +
                '}';
    }
}
