package com.sparta.outsourcing.follow.repository;

import com.sparta.outsourcing.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowCustomRepository {

}
