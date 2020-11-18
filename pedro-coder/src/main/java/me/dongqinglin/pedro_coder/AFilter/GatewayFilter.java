package me.dongqinglin.pedro_coder.AFilter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GatewayFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // System.out.println(request.getHeader("is-auth-success"));
        // System.out.println(request.getHeader("is-auth-success") == "true");
        String isAuthSuccessHeader = request.getHeader("is-auth-success");
        Boolean isAuthSuccess = false;
        if (isAuthSuccessHeader != null) {
            isAuthSuccess = isAuthSuccessHeader.equals("true");
        }

        if(isAuthSuccess){
            // System.out.println("这里这里");
            filterChain.doFilter(request, response);
        }else {
            // System.out.println("哪里哪里");
            response.sendError(403, "权限不足");
        }
    }
}
