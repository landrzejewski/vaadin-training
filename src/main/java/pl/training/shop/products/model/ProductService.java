package pl.training.shop.products.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    public ProductCategory addProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.saveAndFlush(productCategory);
    }

}
