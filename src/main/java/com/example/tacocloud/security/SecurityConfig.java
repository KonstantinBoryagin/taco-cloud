package com.example.tacocloud.security;

import com.example.tacocloud.data.UserRepository;
import com.example.tacocloud.tacos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableGlobalMethodSecurity
//extends WebSecurityConfigurerAdapter
public class SecurityConfig {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
       return username -> {
           User user = userRepo.findByUsername(username);
           if(user != null) {
               return user;
           } else {
               throw new UsernameNotFoundException("User '" + username + "' not found");
           }
       };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/design", "/orders").access("hasRole('USER')")
                .antMatchers("/", "/**").access("permitAll()")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/design")
//                .and()
//                .oauth2Login()
//                .loginPage("/login")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .csrf().disable()
                .build();
    }

}
