package com.sparta.outsourcing.user.entity;

import com.sparta.outsourcing.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
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
    private UserRoleEnum role;

    @Column
    private String intro;

    public void updateUser(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.intro = requestDto.getIntro();
    }
}
