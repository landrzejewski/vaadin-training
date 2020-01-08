package pl.training.shop.products.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class ProductCategory {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private String name;
    private String description;

}
