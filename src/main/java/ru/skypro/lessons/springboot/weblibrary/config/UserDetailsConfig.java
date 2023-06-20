package ru.skypro.lessons.springboot.weblibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class UserDetailsConfig {
    @Bean
    public UserDetailsManager userDetailsManager() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        // Создание базовых пользователей
        userDetailsManager.createUser(User.withUsername("admin").password("1").roles("ADMIN").build());
        userDetailsManager.createUser(User.withUsername("user").password("1").roles("USER").build());
        return userDetailsManager;
    }

}
