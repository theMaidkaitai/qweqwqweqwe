package com.stack.app.cfg.jwt;

import com.stack.app.generalDTO.RolesDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${testing.app.secret}")
    private String secret;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String jwtToken = null;

        if (header != null && header.startsWith("Bearer ")) {
            jwtToken = header.substring(7);
        }

        if (jwtToken != null) { {
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secret)
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();

                String email = claims.getSubject();

                String roles = claims.get("roles", String.class);
                Long id = claims.get("id", Long.class);
                String username = claims.get("nick", String.class);

                UserDetails userDetails = createUserDetailsFromToken(id, email, username, roles);


                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

                // говорим спрингу что этот пользователь норм чел(авторизован), что позволяет юзать @AuthenticationPrincipal


            } catch (ExpiredJwtException e) {
                throw new RuntimeException(e);
            }

        }

        }

        filterChain.doFilter(request, response);
    }


    private UserDetails createUserDetailsFromToken(Long id, String email, String nick, String role) {

        return new UserDetailsImpl(
                id,
                nick,
                email,
                "",
                RolesDTO.valueOf(role)
        );
    }
}
