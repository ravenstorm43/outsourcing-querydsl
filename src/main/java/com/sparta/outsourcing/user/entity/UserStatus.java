package com.sparta.outsourcing.user.entity;

public enum UserStatus {
    USER(Authority.USER),  // 사용자 권한
    ADMIN(Authority.ADMIN),  // 관리자 권한
    KAKAO(Authority.KAKAO),  // 카카오 계정
    WITHDRAW(Authority.WITHDRAW); // 휴면 계정

    private final String authority;

    UserStatus(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }
    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String KAKAO = "ROLE_KAKAO";
        public static final String WITHDRAW = "ROLE_WITHDRAW";
    }
}
