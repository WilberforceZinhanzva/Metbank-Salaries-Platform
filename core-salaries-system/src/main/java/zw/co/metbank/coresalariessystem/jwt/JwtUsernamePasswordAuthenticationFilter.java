package zw.co.metbank.coresalariessystem.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import zw.co.metbank.coresalariessystem.security.AuthenticatedUser;
import zw.co.metbank.coresalariessystem.security.StreamlinedAuthenticatedUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private String jwtSecurityKey = "qwertyuiop1234567yuitrewwwdfgbhjklkmjnzsd567890hgjdfgtreyuhnbvfgsssjkj8765432rtwuh";

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try{
            UsernameAndPassword usernameAndPassword = new ObjectMapper().readValue(request.getInputStream(),UsernameAndPassword.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    usernameAndPassword.getUsername(),
                    usernameAndPassword.getPassword()
            );
            return authenticationManager.authenticate(authentication);
        }catch(IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {


        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities",authResult.getAuthorities())
                .claim("principal",new StreamlinedAuthenticatedUser((AuthenticatedUser) authResult.getPrincipal()))
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(Keys.hmacShaKeyFor(jwtSecurityKey.getBytes()))
                .compact();

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
        response.addHeader("Authorization","Bearer "+token);



    }
}
