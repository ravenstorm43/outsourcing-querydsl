package com.sparta.outsourcing.security;

import com.sparta.outsourcing.user.entity.User;
import com.sparta.outsourcing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userUid) throws UsernameNotFoundException {
        User user = userRepository.findByUserUid(userUid).orElseThrow(
                () -> new UsernameNotFoundException(userUid + " : 계정이 존재하지 않습니다.")
        );
        return new UserDetailsImpl(user);
    }
}