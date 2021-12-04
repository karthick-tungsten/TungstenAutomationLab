package com.tungstenautomationlab.tungstenautomationlab.supports.security;

import com.tungstenautomationlab.tungstenautomationlab.supports.authentication.LoginService;
import com.tungstenautomationlab.tungstenautomationlab.supports.jwt.JwtTokenVerifier;
import com.tungstenautomationlab.tungstenautomationlab.supports.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsRepository userDetailsRepository;
    private final LoginService loginService;
    private final TokenDetails tokenDetails;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsRepository userDetailsRepository, LoginService loginService, TokenDetails tokenDetails) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsRepository = userDetailsRepository;
        this.loginService = loginService;
        this.tokenDetails = tokenDetails;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(),userDetailsRepository))
                .addFilterAfter(new JwtTokenVerifier(userDetailsRepository, tokenDetails), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/superAdmin/create/**").permitAll()
                .antMatchers("/api/v1/superAdmin/resetPassword/**").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/*.js","/*.css").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(loginService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
