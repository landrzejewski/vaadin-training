package pl.training.shop.products.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private String name;
    private String description;
    private long price;
    private Date availableSince;
    private int quantity;
    @JoinColumn(name = "category_id")
    @ManyToOne
    private ProductCategory category;

}
