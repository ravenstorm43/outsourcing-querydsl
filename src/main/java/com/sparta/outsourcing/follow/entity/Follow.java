package com.sparta.outsourcing.follow.entity;

import com.sparta.outsourcing.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "followed_user_id")
    private User followedUser;

    public Follow(User user, User followedUser) {
        this.user = user;
        this.followedUser = followedUser;
    }
}
