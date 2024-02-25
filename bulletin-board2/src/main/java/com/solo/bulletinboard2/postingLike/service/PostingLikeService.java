package com.solo.bulletinboard2.postingLike.service;

import com.solo.bulletinboard2.posting.entity.Posting;
import com.solo.bulletinboard2.posting.service.PostingService;
import com.solo.bulletinboard2.postingLike.entity.PostingLike;
import com.solo.bulletinboard2.postingLike.repository.PostingLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostingLikeService {

    private final PostingLikeRepository postingLikeRepository;
    private final PostingService postingService;

    public PostingLikeService(PostingLikeRepository postingLikeRepository, PostingService postingService) {
        this.postingLikeRepository = postingLikeRepository;
        this.postingService = postingService;
    }

    public Posting createPostingLike(PostingLike postingLike){
        Posting foundPosting = postingService.findVerifiedPosting(postingLike.getPosting().getPostingId());

        PostingLike foundPostingLike = postingLikeRepository.findByMemberMemberIdAndPostingPostingId(
                postingLike.getMember().getMemberId(), postingLike.getPosting().getPostingId());

        if(foundPostingLike == null){
            postingLikeRepository.save(postingLike);
        }
        else{
            postingLikeRepository.delete(foundPostingLike);
        }

        return foundPosting;
    }


}
