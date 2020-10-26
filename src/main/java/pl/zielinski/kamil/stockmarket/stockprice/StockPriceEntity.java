package pl.zielinski.kamil.stockmarket.stockprice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.zielinski.kamil.stockmarket.common.markers.EntityMarker;
import pl.zielinski.kamil.stockmarket.stock.StockEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Builder
@Table(name = "stockprice")
public class StockPriceEntity extends EntityMarker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idstockprice")
    private Long idStockPrice;

    @ManyToOne
    @JoinColumn(name = "ISIN")
    private StockEntity stock;

    @Column(name = "buyout")
    private BigDecimal buyout;

    @Column(name = "sellout")
    private BigDecimal sellout;

    @Column(name = "date")
    private LocalDateTime date;

}
