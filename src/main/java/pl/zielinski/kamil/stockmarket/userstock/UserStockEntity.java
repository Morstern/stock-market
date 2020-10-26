package pl.zielinski.kamil.stockmarket.userstock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.zielinski.kamil.stockmarket.stock.StockEntity;
import pl.zielinski.kamil.stockmarket.user.UserEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "userstock")
public class UserStockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduserstock")
    private Long idStockUser;

    @ManyToOne
    @JoinColumn(name = "ISIN")
    private StockEntity stock;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private UserEntity user;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "date")
    private LocalDateTime date;
}

