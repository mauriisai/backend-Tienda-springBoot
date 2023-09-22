package com.tienda.tiendajwt.service;

import com.tienda.tiendajwt.dao.UserDao;
import com.tienda.tiendajwt.entity.JwtRequest;
import com.tienda.tiendajwt.entity.JwtResponse;
import com.tienda.tiendajwt.entity.User;
import com.tienda.tiendajwt.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class JwtService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    LoginDetailsService loginService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));

        } catch (DisabledException e) {
            throw new Exception("User is disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials from user");
        }
    }

}
