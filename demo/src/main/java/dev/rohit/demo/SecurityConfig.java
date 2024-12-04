//package dev.rohit.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
////import org.springframework.security.core.userdetails.InMemoryUserDetailsManager;
//import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // Disables CSRF protection, if needed
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/zoom/connect", "/zoom/callback").permitAll()  // Allow access to these routes without authentication
//                                .anyRequest().authenticated() // Secure other routes
//                )
//                .oauth2Login(oauth2Login ->
//                        oauth2Login
//                                .loginPage("/login")  // Optional: specify your custom login page if needed
//                                .permitAll() // Allow access to the login page
//                );
//
//        return http.build(); // Use the new build method in Spring Security 5+
//    }
//}
//
