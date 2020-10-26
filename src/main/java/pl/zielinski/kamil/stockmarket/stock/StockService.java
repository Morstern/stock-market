package pl.zielinski.kamil.stockmarket.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.zielinski.kamil.stockmarket.common.constants.APIEndpoints;
import pl.zielinski.kamil.stockmarket.common.constants.UserRoles;
import pl.zielinski.kamil.stockmarket.common.exceptions.DatabaseException;
import pl.zielinski.kamil.stockmarket.common.exceptions.ForbiddenRequestException;
import pl.zielinski.kamil.stockmarket.common.utils.HeaderBuilder;
import pl.zielinski.kamil.stockmarket.common.utils.URICreator;
import pl.zielinski.kamil.stockmarket.common.utils.UserUtils;
import pl.zielinski.kamil.stockmarket.user.UserEntity;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public ResponseEntity<?> getStocks(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(stockRepository.findAll(pageable), HttpStatus.OK);
    }

    public ResponseEntity<?> getStockByISIN(String ISIN) {
        Optional<StockEntity> optionalStockEntity = stockRepository.findById(ISIN);
        if (optionalStockEntity.isPresent()) {
            var headers = new HeaderBuilder().setETag(optionalStockEntity.get().hashCode()).build();
            return new ResponseEntity<>(optionalStockEntity, headers, HttpStatus.OK);
        } else {
            throw new StockNotFoundException();
        }
    }

    public ResponseEntity<?> getStocksByName(String name, Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);
        Optional<List<StockEntity>> optionalStockEntities = stockRepository.findStockEntitiesByNameContaining(name, pageable);
        if (optionalStockEntities.isPresent()) {
            return new ResponseEntity<>(optionalStockEntities.get(), HttpStatus.OK);
        } else {
            throw new StockNotFoundException();
        }
    }

    public ResponseEntity<?> createStock(StockEntity stockEntity) {
        UserEntity currentUser = UserUtils.getCurrentUser();
        if (!currentUser.getRole().equals(UserRoles.ADMIN)) {
            throw new ForbiddenRequestException();
        }
        try {
            StockEntity createdEntity = stockRepository.save(stockEntity);
            var headers = new HeaderBuilder()
                    .setLocation(new URICreator(APIEndpoints.API_STOCKS, createdEntity.getISIN()).getURI())
                    .build();

            return new ResponseEntity<>(createdEntity, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new DatabaseException();
        }

    }

    public ResponseEntity<?> deleteStock(String ISIN, int hashcode) {
        // TODO: check for authorization + if hashcode is okay
        UserEntity currentUser = UserUtils.getCurrentUser();
        if (!currentUser.getRole().equals(UserRoles.ADMIN)) {
            throw new ForbiddenRequestException();
        }
        try {
            // TODO: LOGIC
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new DatabaseException();
        }
    }


}
