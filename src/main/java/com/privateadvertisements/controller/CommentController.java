package com.privateadvertisements.controller;


import com.privateadvertisements.api.repository.CrudComment;
import com.privateadvertisements.model.Comment;
import com.privateadvertisements.model.dto.CommentDto;
import com.privateadvertisements.util.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    private final CrudComment crudComment;
    private final Mapper mapper;

    public CommentController(CrudComment crudComment, Mapper mapper) {
        this.crudComment = crudComment;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<CommentDto>> readAll() {
        List<Comment> comments = crudComment.findAll();
        List<CommentDto> commentDtoList = Mapper.convertList(comments, mapper::convertCommentToConvertDto);
//        List<UserDto> userDtoList = Mapper.convertList(users, mapper::convertUserToUserDto);convertUserToUserDto
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }
}
