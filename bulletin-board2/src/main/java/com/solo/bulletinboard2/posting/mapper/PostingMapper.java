package com.solo.bulletinboard2.posting.mapper;

import com.solo.bulletinboard2.member.entity.Member;
import com.solo.bulletinboard2.posting.dto.PostingDto;
import com.solo.bulletinboard2.posting.entity.Posting;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostingMapper {

    default Posting postingPostDtoToPosting(PostingDto.Post postingPostDto){
        Member member = new Member();
        member.setMemberId(postingPostDto.getMemberId());

        Posting posting = new Posting();
        posting.setTitle(postingPostDto.getTitle());
        posting.setContent(postingPostDto.getContent());
        posting.setMember(member);

        return posting;
    }

    Posting postingPatchDtoToPosting(PostingDto.Patch postingPatchDto);

    default PostingDto.Response postingToPostingResponseDto(Posting posting){
        PostingDto.Response response = PostingDto.Response.builder()
                .postingId(posting.getPostingId())
                .title(posting.getTitle())
                .content(posting.getContent())
                .createdAt(posting.getCreatedAt())
                .modifiedAt(posting.getModifiedAt())
                .build();

        PostingDto.MemberInfo memberInfo = PostingDto.MemberInfo.builder()
                .memberId(posting.getMember().getMemberId())
                .email(posting.getMember().getEmail())
                .nickname(posting.getMember().getNickname())
                .build();

        response.setMemberInfo(memberInfo);

        if(posting.getComments() != null) {
            List<PostingDto.CommentResponse> commentResponses = posting.getComments()
                    .stream().map(comment -> PostingDto.CommentResponse.builder()
                            .commentId(comment.getCommentId())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .commentMemberInfo(PostingDto.CommentMemberInfo.builder()
                                    .memberId(comment.getMember().getMemberId())
                                    .email(comment.getMember().getEmail())
                                    .nickname(comment.getMember().getNickname())
                                    .build())
                            .build())
                    .collect(Collectors.toList());

            response.setCommentResponses(commentResponses);
        }

        return response;
    }

    List<PostingDto.Response> postingsToPostingResponseDtos(List<Posting> postings);

}
