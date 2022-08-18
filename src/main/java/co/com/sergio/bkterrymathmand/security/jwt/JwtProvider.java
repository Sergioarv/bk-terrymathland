package co.com.sergio.bkterrymathmand.security.jwt;

import co.com.sergio.bkterrymathmand.security.dto.JwtDto;
import co.com.sergio.bkterrymathmand.security.entity.UsuarioPrincipal;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @project bk-terry-math-land
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 01/07/2022 16:51
 **/
@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(Authentication authentication) {
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        List<String> roles = usuarioPrincipal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(usuarioPrincipal.getDocumento())
                .claim("roles", roles)
                .claim("id", usuarioPrincipal.getId())
                .claim("nombre", usuarioPrincipal.getUsername())
                .claim("documento", usuarioPrincipal.getDocumento())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 180))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public String getDocumentoUsuarioPorToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token mal formado");
        } catch (UnsupportedJwtException e) {
            logger.error("Token no soportado");
        } catch (ExpiredJwtException e) {
            logger.error("Token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("Token vacío");
        } catch (SignatureException e) {
            logger.error("Fallo con la firma");
        }
        return false;
    }

    public boolean validateName(Authentication authentication, String nombre) {
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();

        if (usuarioPrincipal.getUsername().equalsIgnoreCase(nombre)) {
            return true;
        }
        return false;
    }

    public String refreshToken(JwtDto jwtDTO) throws ParseException {
        JWT jwt = JWTParser.parse(jwtDTO.getToken());
        JWTClaimsSet claims = jwt.getJWTClaimsSet();
        String username = claims.getSubject();
        List<String> roles = (List<String>) claims.getClaim("roles");
        long id = (long) claims.getClaim("id");
        String nombre = (String) claims.getClaim("nombre");
        String documento = (String) claims.getClaim("documento");
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .claim("id", id)
                .claim("nombre", nombre)
                .claim("documento", documento)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration * 180))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
}
