package com.sparta.outsourcing.user.entity;

import com.sparta.outsourcing.user.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uid", nullable = false)
    private String userUid;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatus role;

    @Column
    private String intro;

    public User(SignupRequestDto requestDto, String password) {
        this.userUid = requestDto.getUserUid();
        this.username = requestDto.getUsername();
        this.role = requestDto.getRole();
        this.intro = requestDto.getIntro();
        this.password = password;
    }
}
