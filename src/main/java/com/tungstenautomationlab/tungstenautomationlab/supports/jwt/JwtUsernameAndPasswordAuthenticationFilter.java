package com.tungstenautomationlab.tungstenautomationlab.supports.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.UserDetailsRepository;
import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.Users;
import com.tungstenautomationlab.tungstenautomationlab.supports.constants.Configs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsRepository userDetailsRepository;
    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsRepository userDetailsRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        List<Users> userDetials=userDetailsRepository.findByEmail(authResult.getName());
        String token = Jwts.builder()
                .setSubject(userDetials.get(0).getId())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(Keys.hmacShaKeyFor(Configs.secretKey.getBytes()))
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
    }


}
