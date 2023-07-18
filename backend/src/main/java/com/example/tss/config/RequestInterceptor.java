package com.example.tss.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Log the incoming request details
        logger.info("Request URL: {}", request.getRequestURL());
        logger.info("Request Method: {}", request.getMethod());
        logger.info("Request Parameters: {}", request.getParameterMap());
        // You can log more details as per your requirements

        return true;
    }
}

