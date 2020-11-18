package me.dongqinglin.zuul_gateway.AFilter;

import me.dongqinglin.zuul_gateway.CServiceImpl.MyUserDetailService;
import me.dongqinglin.zuul_gateway.CServiceImplAlgorithm.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MyUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authenticationHeader = request.getHeader("Authorization");
        System.out.println("1 "+authenticationHeader);
        // if (authenticationHeader == null || authenticationHeader.trim().equals("")) return;
        String username = null;
        String jwt = null;
        if(authenticationHeader !=null && authenticationHeader.startsWith("Bearer ")){
            jwt = authenticationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            System.out.println("2 "+username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println("3 添加响应头");
                response.setHeader("isAuthSuccess", "true");
            }
        }


        filterChain.doFilter(request, response);
    }
}