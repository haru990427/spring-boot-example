package com.example.study.filter;

import com.example.study.domain.enums.Role;
import com.example.study.service.user.UserDetailsImpl;
import com.example.study.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        log.debug("========JWT 토큰 검사 시작========");
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username = jwtUtils.extractUsername(jwt);
        log.debug("User Id: {} UserName: {}", jwtUtils.extractUserId(jwt), username);
        log.debug("유효한 사용자인지, SecurityContextHolder에 저장된 값이 있는지 검사");

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.debug("SecurityContextHolder에 사용자 정보 담기 시작");

            Long userId = jwtUtils.extractUserId(jwt);
            List<String> roles = jwtUtils.extractRoles(jwt);

            UserDetailsImpl userDetails = new UserDetailsImpl(userId, username, roles);
            /* todo 코드 정리 */
            log.debug("JWT 토큰이 유효한지 검사 시작: {}", jwtUtils.isTokenExpired(jwt));
            if (jwtUtils.validateToken(jwt, username)) {
//                 var authorities = jwtUtils.extractRoles(jwt).stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                        .collect(Collectors.toList());
//
//                 var authToken = new UsernamePasswordAuthenticationToken(
//                 userDetails, null, authorities);

//                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roles.name()));

                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );


                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.debug("SecurityContextHolder에 사용자 정보 담기 성공");
                log.debug("유저 권한: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            }
        }
        filterChain.doFilter(request, response);
        log.debug("========JWT 토큰 검사 종료========");
    }
}