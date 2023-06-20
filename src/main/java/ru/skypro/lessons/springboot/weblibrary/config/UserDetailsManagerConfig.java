package ru.skypro.lessons.springboot.weblibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class UserDetailsManagerConfig {
    @Bean
    public UserDetailsManager userDetailsManager() {
        UserDetails user1 = User.withUsername("admin")
                .password("1")
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.withUsername("user")
                .password("1")
                .roles("USER")
                .build();

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);

        return userDetailsManager;
    }
}
