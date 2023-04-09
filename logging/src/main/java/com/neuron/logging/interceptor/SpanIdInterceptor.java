package com.neuron.logging.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Service
public class SpanIdInterceptor implements HandlerInterceptor {

    private final ThreadLocal<String> spanIdHolder = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String spanId = UUID.randomUUID().toString();
        spanIdHolder.set(spanId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        spanIdHolder.remove();
    }

    public String getSpanId() {
        return spanIdHolder.get();
    }
}
