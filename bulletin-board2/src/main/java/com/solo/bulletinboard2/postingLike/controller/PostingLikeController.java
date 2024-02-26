package com.solo.bulletinboard2.postingLike.controller;

import com.solo.bulletinboard2.dto.SingleResponseDto;
import com.solo.bulletinboard2.posting.entity.Posting;
import com.solo.bulletinboard2.postingLike.dto.PostingLikeDto;
import com.solo.bulletinboard2.postingLike.mapper.PostingLikeMapper;
import com.solo.bulletinboard2.postingLike.service.PostingLikeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postingLikes")
public class PostingLikeController {

    private final PostingLikeService postingLikeService;
    private final PostingLikeMapper mapper;

    public PostingLikeController(PostingLikeService postingLikeService, PostingLikeMapper mapper) {
        this.postingLikeService = postingLikeService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postPostingLike(@RequestBody @Valid PostingLikeDto.Post postingLikePostDto){

        Posting posting = postingLikeService.createPostingLike(mapper.postingLikePostDtoToPostingLike(postingLikePostDto));

        return new ResponseEntity(new SingleResponseDto<>(mapper.postingToPostingLikeResponseDto(posting)), HttpStatus.OK);
    }
}
