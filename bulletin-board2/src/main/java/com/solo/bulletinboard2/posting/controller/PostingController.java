package com.solo.bulletinboard2.posting.controller;

import com.solo.bulletinboard2.dto.MultiResponseDto;
import com.solo.bulletinboard2.dto.SingleResponseDto;
import com.solo.bulletinboard2.posting.dto.PostingDto;
import com.solo.bulletinboard2.posting.entity.Posting;
import com.solo.bulletinboard2.posting.mapper.PostingMapper;
import com.solo.bulletinboard2.posting.service.PostingService;
import com.solo.bulletinboard2.utils.UriCreator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/postings")
@Validated
public class PostingController {

    private final PostingService postingService;
    private final PostingMapper mapper;
    private static final String POSTING_DEFAULT_URL = "/postings";

    public PostingController(PostingService postingService, PostingMapper mapper) {
        this.postingService = postingService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postPosting(@RequestBody @Valid PostingDto.Post postingPostDto){
        Posting posting = postingService.createPosting(mapper.postingPostDtoToPosting(postingPostDto));
        URI location = UriCreator.createUri(POSTING_DEFAULT_URL, posting.getPostingId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{posting-id}")
    public ResponseEntity patchPosting(@RequestBody @Valid PostingDto.Patch postingPatchDto,
                                       @PathVariable("posting-id") @Positive long postingId){
        postingPatchDto.setPostingId(postingId);
        Posting posting = postingService.updatePosting(mapper.postingPatchDtoToPosting(postingPatchDto));

        return new ResponseEntity(new SingleResponseDto<>(mapper.postingToPostingResponseDto(posting)), HttpStatus.OK);
    }

    @GetMapping("/{posting-id}")
    public ResponseEntity getPosting(@PathVariable("posting-id") @Positive long postingId){
        Posting posting = postingService.findPosting(postingId);

        return new ResponseEntity(new SingleResponseDto<>(mapper.postingToPostingResponseDto(posting)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getPostings(@RequestParam @Positive int page,
                                      @RequestParam @Positive int size){
        Page<Posting> pagePostings = postingService.findPostings(page-1, size);
        List<Posting> postings = pagePostings.getContent();

        return new ResponseEntity(new MultiResponseDto<>(
                mapper.postingsToPostingResponseDtos(postings), pagePostings), HttpStatus.OK);
    }

    @DeleteMapping("/{posting-id}")
    public ResponseEntity deletePosting(@PathVariable("posting-id") @Positive long postingId){
        postingService.deletePosting(postingId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
