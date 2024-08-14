package forum.api.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import forum.api.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    //With @Value annotation, one can inject a property declared in application.properties
    //file in a Spring class
    //Here  the value of api.security.secret created on the file application.properties
    // will be assigned to the String apiSecret
    @Value("${api.security.secret}")
    private String apiSecret;

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            String token = JWT.create()
                    .withIssuer("Forum API")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        DecodedJWT decodedJWT = null;
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            decodedJWT = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("Forum API")
                    // reusable verifier instance
                    .build()
                    .verify(token);
            decodedJWT.getSubject();  //El subject corresponds to username

        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
        }
        if (decodedJWT.getSubject() == null){
            throw new RuntimeException("Verifier it not valid!!");
        }
        return decodedJWT.getSubject();
    }


    //With this method, we make sure the generated token is valid for up to 5 hours.
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(5).toInstant(ZoneOffset.of("-05:00"));
    }


}
