package com.netcracker.converter.impl;

import com.netcracker.converter.Converter;
import com.netcracker.dto.CommentDto;
import com.netcracker.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentConverterImpl implements Converter<Comment, CommentDto> {
    @Override
    public CommentDto toDto(Comment entity) {
        CommentDto commentDto = new CommentDto(entity.getId(), entity.getRating(), entity.getContent(),
                entity.getClient().getId(), entity.getService().getId());
        return commentDto;
    }

    @Override
    public Comment toEntity(CommentDto dto) {
        Comment comment = new Comment(dto.getId(), dto.getRating(), dto.getContent());
        return comment;
    }
}
