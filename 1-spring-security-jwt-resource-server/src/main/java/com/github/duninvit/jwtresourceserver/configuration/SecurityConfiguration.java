package com.github.duninvit.jwtresourceserver.configuration;

import com.github.duninvit.jwtresourceserver.authentication.InitialAuthenticationFilter;
import com.github.duninvit.jwtresourceserver.authentication.JwtAuthenticationFilter;
import com.github.duninvit.jwtresourceserver.authentication.provider.OtpAuthenticationProvider;
import com.github.duninvit.jwtresourceserver.authentication.provider.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private InitialAuthenticationFilter initialAuthenticationFilter;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    @Lazy
    public void setInitialAuthenticationFilter(InitialAuthenticationFilter initialAuthenticationFilter) {
        this.initialAuthenticationFilter = initialAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(usernamePasswordAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.addFilterAt(
                initialAuthenticationFilter,
                BasicAuthenticationFilter.class
        ).addFilterAfter(
                jwtAuthenticationFilter,
                BasicAuthenticationFilter.class
        );

        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
