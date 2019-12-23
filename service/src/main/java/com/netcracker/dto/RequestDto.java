package com.netcracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto extends BaseDto {
    private int clientId;
    private String comment;
    private RequestStatusDto status;

    public RequestDto() {
    }

    public RequestDto(int id, int clientId, String comment, RequestStatusDto status) {
        super(id);
        this.clientId = clientId;
        this.comment = comment;
        this.status = status;
    }

    public RequestDto(int clientId, String comment) {
        this.clientId = clientId;
        this.comment = comment;
        this.status = RequestStatusDto.NEW;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                ", \"comment\":\"" + comment + '\"' +
                ", \"client\":" + clientId +
                ", \"status\":\"" + status + '\"' +
                '}';
    }
}
