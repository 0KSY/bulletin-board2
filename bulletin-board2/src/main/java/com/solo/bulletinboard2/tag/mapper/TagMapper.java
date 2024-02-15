package com.solo.bulletinboard2.tag.mapper;

import com.solo.bulletinboard2.posting.entity.Posting;
import com.solo.bulletinboard2.tag.dto.TagDto;
import com.solo.bulletinboard2.tag.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag tagPostDtoToTag(TagDto.Post tagPostDto);

    TagDto.Response tagToTagResponseDto(Tag tag);

    List<TagDto.Response> tagsToTagResponseDtos(List<Tag> tags);

    default TagDto.PostingResponse postingToPostingResponseDto(Posting posting){

        TagDto.PostingResponse response = TagDto.PostingResponse.builder()
                .postingId(posting.getPostingId())
                .title(posting.getTitle())
                .build();

        TagDto.MemberInfo memberInfo = TagDto.MemberInfo.builder()
                .memberId(posting.getMember().getMemberId())
                .email(posting.getMember().getEmail())
                .nickname(posting.getMember().getNickname())
                .build();

        response.setMemberInfo(memberInfo);

        return response;
    }

    List<TagDto.PostingResponse> postingsToPostingResponseDtos(List<Posting> postings);
}
