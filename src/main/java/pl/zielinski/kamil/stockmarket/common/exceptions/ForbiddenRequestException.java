package pl.zielinski.kamil.stockmarket.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User doesn't have privilege for the request")
public class ForbiddenRequestException extends RuntimeException {
}
