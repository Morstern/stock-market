package pl.zielinski.kamil.stockmarket.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zielinski.kamil.stockmarket.common.constants.APIEndpoints;


@RestController
@RequestMapping(APIEndpoints.API_STOCKS)
public class StockController {
    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<?> getStocks(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return stockService.getStocks(page, size);
    }

    @GetMapping(value = "/{isin}")
    public ResponseEntity<?> getStockByISIN(@PathVariable("isin") String ISIN) {
        return stockService.getStockByISIN(ISIN);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> getStocksByName(@PathVariable("name") String name, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return stockService.getStocksByName(name, page, size);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createStock(@RequestBody StockEntity stockEntity) {
        return stockService.createStock(stockEntity);
    }

    @DeleteMapping(value = "/{isin}")
    public ResponseEntity<?> deleteStock(@PathVariable String isin, @RequestHeader("If-Match") int hashcode) {
        return stockService.deleteStock(isin, hashcode);
    }

    @PutMapping(value = "/")
    public ResponseEntity<?> updateStock(@RequestBody StockEntity stockEntity, @RequestHeader("If-Match") int hashcode) {
//        if (UserUtils.hasCurrentLoggedUserAdminRole()) {
//            try {
//                Optional<Stock> foundStock = stockRepository.findById(stock.getISIN());
//                if (foundStock.isPresent()) {
//                    Stock updated = stockRepository.save(stock);
//                    return new ResponseEntity<>(updated, HttpStatus.OK);
//                } else {
//                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//                }
//            } catch (Exception e) {
//                return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.CONFLICT);
//            }
//        } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
    }

}
