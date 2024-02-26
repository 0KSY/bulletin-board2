package com.solo.bulletinboard2.posting.mapper;

import com.solo.bulletinboard2.member.entity.Member;
import com.solo.bulletinboard2.posting.dto.PostingDto;
import com.solo.bulletinboard2.posting.entity.Posting;
import com.solo.bulletinboard2.postingTag.entity.PostingTag;
import com.solo.bulletinboard2.tag.entity.Tag;
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

        if(postingPostDto.getPostingTagDtos() != null){
            List<PostingTag> postingTags = postingPostDto.getPostingTagDtos().stream()
                    .map(postingTagDto -> {
                        Tag tag = new Tag();
                        tag.setTagId(postingTagDto.getTagId());

                        PostingTag postingTag = new PostingTag();

                        postingTag.setPosting(posting);
                        postingTag.setTag(tag);

                        return postingTag;
                    }).collect(Collectors.toList());

            posting.setPostingTags(postingTags);
        }

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

        if(posting.getPostingLikes() != null){
            response.setLikeCount(posting.getPostingLikes().size());
        }
        else{
            response.setLikeCount(0);
        }

        if(posting.getPostingTags() != null){

            List<PostingDto.TagResponse> tagResponses
                    = posting.getPostingTags().stream()
                    .map(postingTag -> PostingDto.TagResponse.builder()
                            .tagId(postingTag.getTag().getTagId())
                            .tagName(postingTag.getTag().getTagName())
                            .build()
                    ).collect(Collectors.toList());

            response.setTagResponses(tagResponses);

        }

        if(posting.getComments() != null){
            List<PostingDto.ParentCommentResponse> parentCommentResponses
                    = posting.getComments().stream()
                    .filter(comment -> comment.getParent() == null)
                    .map(comment -> PostingDto.ParentCommentResponse.builder()
                            .commentId(comment.getCommentId())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .memberInfo(PostingDto.MemberInfo.builder()
                                    .memberId(comment.getMember().getMemberId())
                                    .email(comment.getMember().getEmail())
                                    .nickname(comment.getMember().getNickname())
                                    .build())
                            .childCommentResponses(comment.getChildren().stream()
                                    .map(childComment -> PostingDto.ChildCommentResponse.builder()
                                            .commentId(childComment.getCommentId())
                                            .content(childComment.getContent())
                                            .createdAt(childComment.getCreatedAt())
                                            .modifiedAt(childComment.getModifiedAt())
                                            .memberInfo(PostingDto.MemberInfo.builder()
                                                    .memberId(childComment.getMember().getMemberId())
                                                    .email(childComment.getMember().getEmail())
                                                    .nickname(childComment.getMember().getNickname())
                                                    .build())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build())
                    .collect(Collectors.toList());

            response.setParentCommentResponses(parentCommentResponses);

        }

        return response;
    }

    default PostingDto.PageResponse postingToPostingPageResponseDto(Posting posting){
        PostingDto.PageResponse pageResponse = PostingDto.PageResponse.builder()
                .postingId(posting.getPostingId())
                .title(posting.getTitle())
                .build();

        PostingDto.MemberInfo memberInfo = PostingDto.MemberInfo.builder()
                .memberId(posting.getMember().getMemberId())
                .email(posting.getMember().getEmail())
                .nickname(posting.getMember().getNickname())
                .build();

        pageResponse.setMemberInfo(memberInfo);

        return pageResponse;
    }

    List<PostingDto.PageResponse> postingsToPostingPageResponseDtos(List<Posting> postings);

}
