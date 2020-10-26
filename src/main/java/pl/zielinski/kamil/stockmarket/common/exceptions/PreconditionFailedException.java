package pl.zielinski.kamil.stockmarket.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Please fetch the newest ETag value")
public class PreconditionFailedException extends RuntimeException {
}
