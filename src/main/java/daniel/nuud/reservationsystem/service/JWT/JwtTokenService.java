//package daniel.nuud.reservationsystem.service.JWT;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.JWTVerifier;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.time.Duration;
//import java.time.Instant;
//
//@Slf4j
//@Service
//public class JwtTokenService {
//
//    private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(20);
//
//    private final Algorithm rsa256;
//    private final JWTVerifier verifier;
//
//    public JwtTokenService(@Value("classpath:certs/public.pem") final RSAPublicKey publicKey,
//                           @Value("classpath:certs/private.pem") final RSAPrivateKey privateKey) {
//        this.rsa256 = Algorithm.RSA256(publicKey, privateKey);
//        this.verifier = JWT.require(this.rsa256).build();
//    }
//
//    public String generateToken(final UserDetails userDetails) {
//        final Instant now = Instant.now();
//        return JWT.create()
//                .withSubject(userDetails.getUsername())
//                .withIssuer("app")
//                .withIssuedAt(now)
//                .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
//                .sign(this.rsa256);
//    }
//
//    public String validateTokenAndGetUsername(final String token) {
//        try {
//            return verifier.verify(token).getSubject();
//        } catch (final JWTVerificationException verificationEx) {
//            log.warn("token invalid: {}", verificationEx.getMessage());
//            return null;
//        }
//    }
//}
