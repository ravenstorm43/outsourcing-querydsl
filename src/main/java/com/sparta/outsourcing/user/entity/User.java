package com.sparta.outsourcing.user.entity;

import com.sparta.outsourcing.user.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uid", nullable = false)
    private String userUid;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column
    private String intro;

    @Column
    private String refreshToken;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatus role;

    public User(SignupRequestDto requestDto, String password) {
        this.userUid = requestDto.getUserUid();
        this.username = requestDto.getUsername();
        this.role = requestDto.getRole();
        this.intro = requestDto.getIntro();
        this.password = password;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateRole(UserStatus role) {
        this.role = role;
    }
}