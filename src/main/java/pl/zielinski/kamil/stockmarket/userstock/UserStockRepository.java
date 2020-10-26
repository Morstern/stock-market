package pl.zielinski.kamil.stockmarket.userstock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStockRepository extends JpaRepository<UserStockEntity, Long> {
    List<UserStockEntity> findAllByUserIdUser(Long id);
}
