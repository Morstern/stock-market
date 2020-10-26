package pl.zielinski.kamil.stockmarket.common.interceptors.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Slf4j
@Component
public class NoAuthorizationInterceptor implements HandlerInterceptor {

    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    private static final List<String> HttpMethodsForAuthorizedUsers = List.of(POST, PUT, DELETE);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAnonymous = checkIfAnonymous(authentication);
        if (HttpMethodsForAuthorizedUsers.contains(request.getMethod()) && isAnonymous) {
            throw new NoAuthorizationException();
        }

        return true;
    }

    private boolean checkIfAnonymous(Authentication authentication) {
        return authentication.getName().equals("anonymousUser");
    }
}
