package forum.api.infrastructure.security;

import forum.api.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserRepository userRepository;

    @Autowired
    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //"Authorization" corresponds to the name of the header, it is the standard value
        //By using Insomnia, when selecting the Auth method Bearer Token, the default
        //prefix that returns includes the String "Bearer", therefore, with .replace(),
        //we make sure to replace it with an empty String

        var authHeader = request.getHeader("Authorization");
        if (authHeader != null){
            var token = authHeader.replace("Bearer ", "");
            //System.out.println("JWT: " + token);
            System.out.println(tokenService.getSubject(token));

            var userName = tokenService.getSubject(token);
            if (userName != null){
                var user = userRepository.findByUsername(userName);
                var authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities()); //It forces login

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //With this we make sure a filter chain is applied that allows to receive the
        //requests coming from the clients side
        filterChain.doFilter(request, response);
    }
}
