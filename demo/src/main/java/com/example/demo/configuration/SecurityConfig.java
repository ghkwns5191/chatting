package com.example.demo.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .authorizeHttpRequests(
//                        (auth) -> auth.requestMatchers("/").permitAll()
//                ).authorizeHttpRequests(
//                		(auth) -> auth.requestMatchers("/login").permitAll()
//                ).authorizeHttpRequests(
//                		(auth) -> auth.requestMatchers("/ws/chat").permitAll()
//               );
                
                

                .authorizeHttpRequests((auth)->auth.requestMatchers("/"
                													, "/login"
                													, "/ws/chat"
                													, "/room"
                													, "/chatting").permitAll()
                
                
                		);
        		http.formLogin((auth) -> auth.loginPage("/login"));
        return http.build();
    }
    
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
