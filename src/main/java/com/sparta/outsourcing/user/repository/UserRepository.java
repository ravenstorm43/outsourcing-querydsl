package com.sparta.outsourcing.user.repository;

import com.sparta.outsourcing.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
