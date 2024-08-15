package forum.api.controller;

import forum.api.domain.user.User;
import forum.api.domain.user.UserAuthenticationData;
import forum.api.infrastructure.security.JWTTokenData;
import forum.api.infrastructure.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid UserAuthenticationData userAuthenticationData){
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                userAuthenticationData.username(), userAuthenticationData.password());

        var authenticatedUser = authenticationManager.authenticate(authToken);
        var JWTToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());

        return ResponseEntity.ok(new JWTTokenData(JWTToken));
    }
}
