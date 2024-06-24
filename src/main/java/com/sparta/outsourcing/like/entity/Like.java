package com.sparta.outsourcing.like.entity;

import com.sparta.outsourcing.common.Timestamped;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "likes")
public class Like extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "contentid")
    private Long contentId;

    @Column(name = "contenttype")
    @Enumerated(value = EnumType.STRING)
    private LikeType contentType;

    public Like(Long userId, Long contentId, LikeType contentType) {
        this.userId = userId;
        this.contentId = contentId;
        this.contentType = contentType;
    }
}
