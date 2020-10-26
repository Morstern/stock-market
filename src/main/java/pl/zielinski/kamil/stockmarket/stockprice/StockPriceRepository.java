package pl.zielinski.kamil.stockmarket.stockprice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepository extends JpaRepository<StockPriceEntity, Long> {
}
