package com.jobtrail.api.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jobtrail.api.core.exceptions.CustomHttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtTokenFilter extends GenericFilterBean {
    private JwtTokenProvider jwtTokenProvider;

    private final List<String> ignoreUrls = Arrays.asList(
            "/api/account/signin", "/api/account/signup"
    );

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        if (!(req instanceof HttpServletRequest) || !(res instanceof HttpServletResponse)) {
            filterChain.doFilter(req, res);
            return;
        }

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        String url = request.getRequestURI();
        boolean bypassAuth  = ignoreUrls.contains(url);

        try {
            String token = jwtTokenProvider.resolveToken(request);

            if (!bypassAuth && token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (CustomHttpException ex) {
            response.sendError(ex.getHttpStatus().value(), ex.getMessage());
            throw ex;
        }

        filterChain.doFilter(req, res);
    }

}