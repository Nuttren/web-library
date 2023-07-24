package ru.skypro.lessons.springboot.weblibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

public class TestSecurityConfig {

    public PasswordEncoder testPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean (name = "testSecurityFilterChain")
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(authorize -> authorize.anyRequest().permitAll())
                .formLogin().disable()
                .logout().disable();

        return http.build();
    }
}
