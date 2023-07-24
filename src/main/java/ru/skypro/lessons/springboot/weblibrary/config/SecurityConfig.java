package ru.skypro.lessons.springboot.weblibrary.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.lessons.springboot.weblibrary.service.UserService;

@EnableWebSecurity

@Configuration
@RequiredArgsConstructor
public class SecurityConfig  {

    private final UserService userService;
    private final UserDetailsManager userDetailsManager;
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsManager)
                .passwordEncoder(passwordEncoder());
    }

    @Bean (name = "securityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(authorize -> {
                    try {
                        authorize
                                .antMatchers("/admin/**").hasRole("ADMIN")
                                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                                .antMatchers("/public/**").hasAnyRole("USER", "ADMIN");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .formLogin().permitAll()
                .and()
                .logout().logoutSuccessUrl("/login").permitAll();

        return http.build();
    }


    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
}

    }


