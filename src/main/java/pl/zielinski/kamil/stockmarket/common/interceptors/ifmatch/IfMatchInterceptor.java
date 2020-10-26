package pl.zielinski.kamil.stockmarket.common.interceptors.ifmatch;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class IfMatchInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getHeader("If-Match") == null) {
            throw new EmptyIfMatchHeaderException();
        }
        return true;
    }
}
