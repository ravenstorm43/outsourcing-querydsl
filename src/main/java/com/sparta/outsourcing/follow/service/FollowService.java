package com.sparta.outsourcing.follow.service;

import com.sparta.outsourcing.follow.entity.Follow;
import com.sparta.outsourcing.follow.repository.FollowRepository;
import com.sparta.outsourcing.user.entity.User;
import com.sparta.outsourcing.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;

    public ResponseEntity<String> toggleUserFollow(User user, Long followUserId) {
        User followUser = validateUserFollow(followUserId);
        Optional<Follow> followOptional = findFollow(user.getId(), followUserId);

        if (followOptional.isPresent()) {
            followRepository.delete(followOptional.get());
            return ResponseEntity.ok("UnFollowed" + HttpStatus.OK.value());
        } else {
            Follow follow = new Follow(user, followUser);
            followRepository.save(follow);
            return ResponseEntity.ok("Followed" + HttpStatus.OK.value());
        }
    }

    private Optional<Follow> findFollow(Long userId, Long followUserId) {
        return followRepository.findByUserIdAndFollowUserId(userId, followUserId);
    }

    public User validateUserFollow(Long userId) {
        return userService.findById(userId);
    }
}
