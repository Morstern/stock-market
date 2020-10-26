package pl.zielinski.kamil.stockmarket.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Passwords aren't identical")
class PasswordUnmatchedException extends RuntimeException {

}
