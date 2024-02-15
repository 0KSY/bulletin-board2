package com.solo.bulletinboard2.tag.controller;

import com.solo.bulletinboard2.dto.MultiResponseDto;
import com.solo.bulletinboard2.dto.SingleResponseDto;
import com.solo.bulletinboard2.posting.entity.Posting;
import com.solo.bulletinboard2.tag.dto.TagDto;
import com.solo.bulletinboard2.tag.entity.Tag;
import com.solo.bulletinboard2.tag.mapper.TagMapper;
import com.solo.bulletinboard2.tag.service.TagService;
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
@RequestMapping("/tags")
@Validated
public class TagController {

    private final TagService tagService;
    private final TagMapper mapper;

    private static final String TAG_DEFAULT_URL = "/tags";

    public TagController(TagService tagService, TagMapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postTag(@RequestBody @Valid TagDto.Post tagPostDto){
        Tag tag = tagService.createTag(mapper.tagPostDtoToTag(tagPostDto));

        URI location = UriCreator.createUri(TAG_DEFAULT_URL, tag.getTagId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{tag-id}")
    public ResponseEntity getTag(@PathVariable("tag-id") @Positive long tagId){
        Tag tag = tagService.findTag(tagId);

        return new ResponseEntity(new SingleResponseDto<>(mapper.tagToTagResponseDto(tag)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTags(@RequestParam @Positive int page,
                                   @RequestParam @Positive int size){
        Page<Tag> pageTags = tagService.findTags(page-1, size);

        List<Tag> tags = pageTags.getContent();

        return new ResponseEntity(new MultiResponseDto<>(mapper.tagsToTagResponseDtos(tags), pageTags), HttpStatus.OK);
    }

    @GetMapping("/tagName")
    public ResponseEntity getPostingsByTagName(@RequestParam String tagName,
                                               @RequestParam @Positive int page,
                                               @RequestParam @Positive int size){

        Page<Posting> pagePostings = tagService.findPostingsByTagName(tagName, page-1, size);
        List<Posting> postings = pagePostings.getContent();

        return new ResponseEntity(
                new MultiResponseDto<>(mapper.postingsToPostingResponseDtos(postings), pagePostings), HttpStatus.OK);

    }
}
