package com.cabreu.candidatesmanagementservice.config;

import com.cabreu.candidatesmanagementservice.adapter.jpa.entity.RoleEntity;
import com.cabreu.candidatesmanagementservice.adapter.jpa.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repo.findByUsername(username)
                .map(user -> {
                    var authorities = user.getRoles().stream()
                            .map(RoleEntity::getName)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toSet());

                    return User.withUsername(user.getUsername())
                            .password(user.getPassword())
                            .authorities(authorities)
                            .build();
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

