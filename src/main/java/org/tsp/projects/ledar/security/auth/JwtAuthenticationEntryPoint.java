package org.tsp.projects.ledar.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException, ServletException {
        log.error("Responding with unauthorized error. Message - {}", e.getMessage());
        String message = e.getMessage();
        if (message != null && message.equals("Bad credentials")) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Sorry, You're not authorized to access this resource. Bad Credentials");

        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Sorry, You're not authorized to access this resource.");
        }
    }
}
