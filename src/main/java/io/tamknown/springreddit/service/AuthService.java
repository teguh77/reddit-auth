package io.tamknown.springreddit.service;

import io.jsonwebtoken.Jwts;
import io.tamknown.springreddit.config.JwtConfig;
import io.tamknown.springreddit.dto.LoginDTO;
import io.tamknown.springreddit.dto.RefreshRequest;
import io.tamknown.springreddit.dto.RegisterRequest;
import io.tamknown.springreddit.entities.Token;
import io.tamknown.springreddit.entities.User;
import io.tamknown.springreddit.exception.ResourceException;
import io.tamknown.springreddit.repository.TokenRepository;
import io.tamknown.springreddit.repository.UserRepository;
import io.tamknown.springreddit.security.user.AppUserDetailsService;
import io.tamknown.springreddit.security.user.ApplicationUserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static io.tamknown.springreddit.security.user.ApplicationUserRole.*;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final TokenRepository tokenRepository;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final AppUserDetailsService appUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authManager,
                       TokenRepository tokenRepository,
                       SecretKey secretKey,
                       JwtConfig jwtConfig,
                       UserRepository userRepository,
                       AppUserDetailsService appUserDetailsService,
                       PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.tokenRepository = tokenRepository;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
        this.appUserDetailsService = appUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest registerRequest) {
        if(!userRepository.existsByUsername(registerRequest.getUsername())) {
            User user = User.newBuilder()
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .email(registerRequest.getEmail())
                    .roles(USER.name())
                    .created(Instant.now())
                    .enabled(true)
                    .build();

            userRepository.save(user);
        } else {
            throw new ResourceException(HttpStatus.UNPROCESSABLE_ENTITY, "User already Exist");
        }
    }

    public ResponseEntity<?> login(LoginDTO login, HttpServletResponse response) {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
            Authentication authentication = authManager.authenticate(auth);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return generateToken(authentication, response);
    }

    public ResponseEntity<?> refresh(RefreshRequest refreshRequest, HttpServletResponse response) {
        if(validateToken(refreshRequest.getToken())) {
            UserDetails userDetails = appUserDetailsService.loadUserByUsername(refreshRequest.getUsername());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
            Authentication authentication = authManager.authenticate(auth);

            // delete token while generate new token
            tokenRepository.deleteByQwerty(refreshRequest.getToken());
            return generateToken(authentication, response);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Access denied, Unauthorized");
        }
    }


    public void logout(RefreshRequest refreshRequest, HttpServletResponse response) {
        try {
            tokenRepository.deleteByQwerty(refreshRequest.getToken());
            Cookie cookie = new Cookie("qwerty", null);
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);
        } catch (IllegalStateException e){
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Cannot logout");
        }
    }

//  ############################### HELPER METHOD ###############################
    private ResponseEntity<?> generateToken(Authentication authentication, HttpServletResponse response) {

        String randString = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("qwerty", randString);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        Token saveCookie = Token
                .newBuilder()
                .qwerty(cookie.getValue())
                .createdDate(Instant.now())
                .build();
        tokenRepository.save(saveCookie);
        response.addCookie(cookie);

        String jwt = Jwts
                    .builder()
                    .setSubject(authentication.getName())
                    .claim("authorities", authentication.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(7)))
                    .signWith(secretKey)
                    .compact();

        HashMap<String, String> token = new HashMap<>();
        token.put("token", jwtConfig.getTokenPrefix()+jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }

    boolean validateToken(String token) {
        Optional<Token> qwerty = tokenRepository.findByQwerty(token);
        return qwerty.isPresent();
    }
}
