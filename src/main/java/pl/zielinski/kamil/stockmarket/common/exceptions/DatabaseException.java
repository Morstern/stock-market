package pl.zielinski.kamil.stockmarket.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something went bad on our side, try again later.")
public class DatabaseException extends RuntimeException {
}
