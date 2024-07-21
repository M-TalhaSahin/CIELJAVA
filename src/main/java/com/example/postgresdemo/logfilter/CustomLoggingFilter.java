package com.example.postgresdemo.logfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CustomLoggingFilter.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpServletRequest);
        CachedBodyHttpServletResponse cachedResponse = new CachedBodyHttpServletResponse(httpServletResponse);

        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDateTime = dateTime.format(dateTimeFormatter);

        String requestBody = new String(cachedRequest.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String requestIP = httpServletRequest.getRemoteAddr();

        logger.info("Request Log - Time: {}, IP: {}, Method: {}, URI: {}, Body: {}",
                formattedDateTime, requestIP, cachedRequest.getMethod(), cachedRequest.getRequestURI(), requestBody);

        chain.doFilter(cachedRequest, cachedResponse);

        String responseBody = new String(cachedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

        logger.info("Response Log - Time: {}, Status: {}, Body: {}",
                formattedDateTime, cachedResponse.getStatus(), responseBody);

        // Write the cached response body to the actual response
        httpServletResponse.getOutputStream().write(cachedResponse.getContentAsByteArray());
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
