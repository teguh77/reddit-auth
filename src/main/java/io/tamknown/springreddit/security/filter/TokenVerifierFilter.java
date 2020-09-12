package io.tamknown.springreddit.security.filter;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.tamknown.springreddit.config.JwtConfig;
import io.tamknown.springreddit.security.user.AppUserDetailsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class TokenVerifierFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final AppUserDetailsService appUserDetailsService;

    public TokenVerifierFilter(JwtConfig jwtConfig,
                               SecretKey secretKey,
                               AppUserDetailsService appUserDetailsService) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.appUserDetailsService = appUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        if (Strings.isNullOrEmpty(authorizationHeader) ||
                !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
        try {

            Jws<Claims> claimsJws = Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();
            String jwtUsername = body.getSubject();

            UserDetails user = appUserDetailsService.loadUserByUsername(jwtUsername);

            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthority = authorities
                    .stream()
                    .map(mapItem -> new SimpleGrantedAuthority(mapItem.get("authority")))
                    .collect(Collectors.toSet());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    null,
                    user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (JwtException e) {
            throw new IllegalStateException("This user cannot be trusted");
        }
        chain.doFilter(request, response);
    }
}
