package com.solo.bulletinboard2.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MemberDto {

    @Getter
    @Setter
    public static class Post{
        @NotNull
        private String email;
        @NotNull
        private String nickname;
    }

    @Getter
    @Setter
    public static class Patch{
        private long memberId;
        private String nickname;
    }

    @Getter
    @Setter
    @Builder
    public static class Response{
        private long memberId;
        private String email;
        private String nickname;
    }
}
