package pl.zielinski.kamil.stockmarket.common.interceptors.ifmatch;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_REQUIRED, reason = "Please supply If-Match value, which you can get from GET on resource")
class EmptyIfMatchHeaderException extends RuntimeException {
}
