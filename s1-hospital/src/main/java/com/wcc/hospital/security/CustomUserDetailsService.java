package com.wcc.hospital.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        if ("doctor".equals(username)) {
            return User.builder()
                    .username("doctor")
                    .password(encoder.encode("password"))
                    .roles("DOCTOR")
                    .build();
        }

        if ("nurse".equals(username)) {
            return User.builder()
                    .username("nurse")
                    .password(encoder.encode("password"))
                    .roles("NURSE")
                    .build();
        }

        if ("receptionist".equals(username)) {
            return User.builder()
                    .username("receptionist")
                    .password(encoder.encode("password"))
                    .roles("RECEPTIONIST")
                    .build();
        }

        throw new UsernameNotFoundException("User not found");
    }
}