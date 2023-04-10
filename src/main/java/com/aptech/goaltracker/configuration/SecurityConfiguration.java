package com.aptech.goaltracker.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable().csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**").permitAll()
                        .antMatchers("/","/login", "/signup").permitAll()
                        .antMatchers("/lk").hasAuthority("USER")
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureForwardUrl("/error")
                        .defaultSuccessUrl("/lk", true)
                        .permitAll()
                )
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();

        return http.build();
    }
}
