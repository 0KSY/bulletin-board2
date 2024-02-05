package com.solo.bulletinboard2.comment.controller;

import com.solo.bulletinboard2.comment.dto.CommentDto;
import com.solo.bulletinboard2.comment.entity.Comment;
import com.solo.bulletinboard2.comment.mapper.CommentMapper;
import com.solo.bulletinboard2.comment.service.CommentService;
import com.solo.bulletinboard2.dto.SingleResponseDto;
import com.solo.bulletinboard2.utils.UriCreator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/comments")
@Validated
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper mapper;
    private static final String COMMENT_DEFAULT_URL = "/comments";

    public CommentController(CommentService commentService, CommentMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postComment(@RequestBody @Valid CommentDto.Post commentPostDto){
        Comment comment = commentService.createComment(mapper.commentPostDtoToComment(commentPostDto));
        URI location = UriCreator.createUri(COMMENT_DEFAULT_URL, comment.getCommentId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment(@RequestBody @Valid CommentDto.Patch commentPatchDto,
                                       @PathVariable("comment-id") @Positive long commentId){
        commentPatchDto.setCommentId(commentId);

        Comment comment = commentService.updateComment(mapper.commentPatchDtoToComment(commentPatchDto));

        return new ResponseEntity(new SingleResponseDto<>(mapper.commentToCommentResponseDto(comment)), HttpStatus.OK);
    }
}
