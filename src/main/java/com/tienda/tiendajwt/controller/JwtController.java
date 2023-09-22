package com.tienda.tiendajwt.controller;

import com.tienda.tiendajwt.entity.JwtRequest;
import com.tienda.tiendajwt.entity.JwtResponse;
import com.tienda.tiendajwt.entity.User;
import com.tienda.tiendajwt.service.JwtService;
import com.tienda.tiendajwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    /*@PostMapping("/authenticate")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest.getUserName());
        return jwtService.createJwtToken(jwtRequest);
    }
    */

    @PostMapping("/authenticate")
    public ResponseEntity createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest.getUserName());
        // Generamos un token con el Metodo en mencion.
        Authentication authToken = new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(),
                jwtRequest.getUserPassword());

        // Declaramos en una variable el user Autenticado.
        var usuarioAutenticado = authenticationManager.authenticate(authToken);

        // Retornamos el token.
        if (usuarioAutenticado != null) {
            var JWTtoken = jwtUtil.generateToken((UserDetails) usuarioAutenticado.getPrincipal());
            System.out.println(JWTtoken);
            return ResponseEntity.ok(new JwtResponse(JWTtoken));
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }
    }
}
