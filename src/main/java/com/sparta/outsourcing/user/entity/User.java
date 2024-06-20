package com.sparta.outsourcing.user.entity;

import com.sparta.outsourcing.common.Timestamped;
import com.sparta.outsourcing.user.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.outsourcing.user.dto.UpdateUserRequestDto;

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
    public void updateUser(UpdateUserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.intro = requestDto.getIntro();
    }
    public void updatePassword(String password) {
        this.password = password;
    }
}