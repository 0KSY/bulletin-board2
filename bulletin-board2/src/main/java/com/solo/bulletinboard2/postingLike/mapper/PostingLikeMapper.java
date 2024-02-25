package com.solo.bulletinboard2.postingLike.mapper;

import com.solo.bulletinboard2.member.entity.Member;
import com.solo.bulletinboard2.posting.entity.Posting;
import com.solo.bulletinboard2.postingLike.dto.PostingLikeDto;
import com.solo.bulletinboard2.postingLike.entity.PostingLike;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostingLikeMapper {

    default PostingLike postingLikePostDtoToPostingLike(PostingLikeDto.Post postingLikePostDto){
        Member member = new Member();
        member.setMemberId(postingLikePostDto.getMemberId());

        Posting posting = new Posting();
        posting.setPostingId(postingLikePostDto.getPostingId());

        PostingLike postingLike = new PostingLike();
        postingLike.setMember(member);
        postingLike.setPosting(posting);

        return postingLike;
    }

    default PostingLikeDto.Response postingToPostingLikeResponseDto(Posting posting){
        PostingLikeDto.Response response = PostingLikeDto.Response.builder()
                .postingId(posting.getPostingId())
                .likeCount(posting.getPostingLikes().size())
                .build();

        return response;
    }
}
