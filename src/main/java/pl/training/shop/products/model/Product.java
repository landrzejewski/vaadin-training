package pl.training.shop.products.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "[a-zA-Z]+")
    @NonNull
    private String name;
    @NotEmpty
    private String description;
    private long price;
    private Date availableSince;
    private int quantity;
    @JoinColumn(name = "category_id")
    @ManyToOne
    private ProductCategory category;

}
