package pl.zielinski.kamil.stockmarket.stock;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Stock wasn't found in our database")
public class StockNotFoundException extends RuntimeException {
}
