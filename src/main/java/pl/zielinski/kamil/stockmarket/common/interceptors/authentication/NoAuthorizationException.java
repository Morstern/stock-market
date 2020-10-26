package pl.zielinski.kamil.stockmarket.common.interceptors.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Please log in")
class NoAuthorizationException extends RuntimeException {
}
