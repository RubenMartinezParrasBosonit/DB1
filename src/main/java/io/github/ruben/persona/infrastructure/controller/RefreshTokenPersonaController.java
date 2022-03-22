package io.github.ruben.persona.infrastructure.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ruben.persona.application.PersonaService;
import io.github.ruben.persona.domain.Persona;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("persona")
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET})
public class RefreshTokenPersonaController {

    @Autowired
    PersonaService personaService;

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                PersonaOutputDto user =
                    personaService
                    .filtrarPersonaPorNombreUsuario(username, "simple")
                    .getPersonaOutputDtoList()
                    .get(0);
                Map<String, String> role = new HashMap<>();
                if(user.getAdmin()){
                    role.put("roles", "ADMIN");
                }else{
                    role.put("roles", "USER");

                }
                String refresh_token = JWT.create()
                        .withSubject(user.getUsuario())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 *60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", role)
                        .sign(algorithm);


                Map<String, String> tokens = new HashMap<>();
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            }catch(Exception e){
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }
        }else{
            throw new RuntimeException("Refresh token is missing");
        }

    }

}
