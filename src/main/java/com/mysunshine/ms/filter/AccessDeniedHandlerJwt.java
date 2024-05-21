package com.mysunshine.ms.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysunshine.ms.constant.constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccessDeniedHandlerJwt implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_FORBIDDEN);
        body.put("error", Constants.ACCESS_DENIED);
        body.put("message", e.getMessage());
        body.put("path", httpServletRequest.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(httpServletResponse.getOutputStream(), body);
    }
}