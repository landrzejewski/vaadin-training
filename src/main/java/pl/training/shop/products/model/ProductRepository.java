package pl.training.shop.products.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("update Product p set p.quantity = p.quantity - :amount where p.id = :id")
    @Transactional
    @Modifying
    void decreaseQuantity(@Param("id") Long id, @Param("amount") int amount);

    @Query("update Product p set p.quantity = p.quantity + :amount where p.id = :id")
    @Transactional
    @Modifying
    void increaseQuantity(@Param("id") Long id, @Param("amount") int amount);

}
