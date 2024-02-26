package com.solo.bulletinboard2.comment.mapper;

import com.solo.bulletinboard2.comment.dto.CommentDto;
import com.solo.bulletinboard2.comment.entity.Comment;
import com.solo.bulletinboard2.member.entity.Member;
import com.solo.bulletinboard2.posting.entity.Posting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    // post -> entity
    // patch -> entity
    // entity -> response

    default Comment commentPostDtoToComment(CommentDto.Post commentPostDto){
        Member member = new Member();
        member.setMemberId(commentPostDto.getMemberId());

        Posting posting = new Posting();
        posting.setPostingId(commentPostDto.getPostingId());

        Comment comment = new Comment();
        comment.setContent(commentPostDto.getContent());
        comment.setMember(member);
        comment.setPosting(posting);

        if(commentPostDto.getParentId() != 0){
            Comment parentComment = new Comment();
            parentComment.setCommentId(commentPostDto.getParentId());

            comment.setParent(parentComment);
        }

        return comment;
    }

    Comment commentPatchDtoToComment(CommentDto.Patch commentPatchDto);

    default CommentDto.Response commentToCommentResponseDto(Comment comment){
        CommentDto.Response response = CommentDto.Response.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .postingId(comment.getPosting().getPostingId())
                .build();

        CommentDto.MemberInfo memberInfo = CommentDto.MemberInfo.builder()
                .memberId(comment.getMember().getMemberId())
                .email(comment.getMember().getEmail())
                .nickname(comment.getMember().getNickname())
                .build();

        response.setMemberInfo(memberInfo);

        return response;
    }
}
