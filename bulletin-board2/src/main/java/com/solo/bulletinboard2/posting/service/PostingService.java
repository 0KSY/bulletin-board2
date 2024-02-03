package com.solo.bulletinboard2.posting.service;

import com.solo.bulletinboard2.exception.BusinessLogicException;
import com.solo.bulletinboard2.exception.ExceptionCode;
import com.solo.bulletinboard2.posting.entity.Posting;
import com.solo.bulletinboard2.posting.repository.PostingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class PostingService {

    private final PostingRepository postingRepository;

    public PostingService(PostingRepository postingRepository) {
        this.postingRepository = postingRepository;
    }

    public Posting findVerifiedPosting(long postingId){
        Optional<Posting> optionalPosting = postingRepository.findById(postingId);

        Posting foundPosting = optionalPosting
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POSTING_NOT_FOUND));

        return foundPosting;
    }

    public Posting createPosting(Posting posting){
        return postingRepository.save(posting);
    }

    public Posting updatePosting(Posting posting){
        Posting findPosting = findVerifiedPosting(posting.getPostingId());

        Optional.ofNullable(posting.getTitle())
                .ifPresent(title -> findPosting.setTitle(title));
        Optional.ofNullable(posting.getContent())
                .ifPresent(content -> findPosting.setContent(content));

        findPosting.setModifiedAt(LocalDateTime.now());

        return postingRepository.save(findPosting);
    }

    public Posting findPosting(long postingId){
        return findVerifiedPosting(postingId);
    }

    public Page<Posting> findPostings(int page, int size){
        return postingRepository.findAll(
                PageRequest.of(page, size, Sort.by("postingId").descending()));
    }

    public void deletePosting(long postingId){
        Posting foundPosting = findVerifiedPosting(postingId);
        postingRepository.delete(foundPosting);
    }
}
