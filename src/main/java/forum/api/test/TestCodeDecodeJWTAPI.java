package forum.api.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import forum.api.domain.user.User;

public class TestCodeDecodeJWTAPI {

    private final String apiSecret = "123456";

    public String generateToken(String userName, long id){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            String token = JWT.create()
                    .withIssuer("test_auth0")
                    .withSubject(userName)
                    .withClaim("id", id)
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception){
            throw new RuntimeException(exception);
        }
    }

    public String decodeToken(String token){
        DecodedJWT decodedJWT;
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("test_auth0")
                    .build();

            decodedJWT = verifier.verify(token);
            decodedJWT.getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException(exception);
        }
        return decodedJWT.getSubject();
    }

    public static void main(String[] args) {
        TestCodeDecodeJWTAPI testCodeDecodeJWTAPI = new TestCodeDecodeJWTAPI();

        String token = testCodeDecodeJWTAPI.generateToken("julian.lopez", 1);
        System.out.println("JWT: " + token);

        String tokenDecoded = testCodeDecodeJWTAPI.decodeToken(token);
        System.out.println(tokenDecoded);
    }
}
