package com.solo.bulletinboard2.tag.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class TagDto {

    @Getter
    @Setter
    public static class Post{
        @NotNull
        private String tagName;
    }

    @Getter
    @Setter
    @Builder
    public static class Response{
        private long tagId;
        private String tagName;
    }

    @Getter
    @Setter
    @Builder
    public static class PostingResponse{
        private long postingId;
        private String title;
        private MemberInfo memberInfo;
    }

    @Getter
    @Setter
    @Builder
    public static class MemberInfo{
        private long memberId;
        private String email;
        private String nickname;
    }

}
