package com.solo.bulletinboard2.tag.service;

import com.solo.bulletinboard2.exception.BusinessLogicException;
import com.solo.bulletinboard2.exception.ExceptionCode;
import com.solo.bulletinboard2.posting.entity.Posting;
import com.solo.bulletinboard2.posting.repository.PostingRepository;
import com.solo.bulletinboard2.tag.entity.Tag;
import com.solo.bulletinboard2.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;
    private final PostingRepository postingRepository;

    public TagService(TagRepository tagRepository, PostingRepository postingRepository) {
        this.tagRepository = tagRepository;
        this.postingRepository = postingRepository;
    }

    public void verifyExistsTagName(String tagName){
        Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);

        if(optionalTag.isPresent()){
            throw new BusinessLogicException(ExceptionCode.TAG_EXISTS);
        }
    }

    public Tag findVerifiedTag(long tagId){
        Optional<Tag> optionalTag = tagRepository.findById(tagId);

        Tag foundTag = optionalTag
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TAG_NOT_FOUND));

        return foundTag;
    }

    public Tag findVerifiedTagByTagName(String tagName){
        Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);

        Tag foundTag = optionalTag
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TAG_NOT_FOUND));

        return foundTag;
    }

    public Tag createTag(Tag tag){
        verifyExistsTagName(tag.getTagName());

        return tagRepository.save(tag);
    }

    public Tag findTag(long tagId) {
        return findVerifiedTag(tagId);
    }

    public Page<Tag> findTags(int page, int size){
        return tagRepository.findAll(
                PageRequest.of(page, size, Sort.by("tagId")));
    }

    public Page<Posting> findPostingsByTagName(String tagName, int page, int size){
        Tag foundTag = findVerifiedTagByTagName(tagName);

        List<Long> postingIds = foundTag.getPostingTags().stream()
                .map(postingTag -> postingTag.getPosting().getPostingId()).collect(Collectors.toList());

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("postingId").descending());

        Page<Posting> pagePostings = postingRepository.findByPostingIdIn(postingIds, pageRequest);

        return pagePostings;

    }

}
