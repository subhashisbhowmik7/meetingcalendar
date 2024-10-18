package com.freightfox.meetingcalendar.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF protection for the entire API
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                // Define access control for different endpoints
                // .requestMatchers(HttpMethod.GET, "/employees").authenticated()  // GET /employees needs authentication
                // .requestMatchers(HttpMethod.POST, "/employees").authenticated()  // POST /employees needs authentication
                // .requestMatchers(HttpMethod.DELETE, "/employees/*").authenticated()  // DELETE /employees/{id} needs authentication
                .anyRequest().permitAll()  // Allow all other requests without authentication
            );
            

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   
}
