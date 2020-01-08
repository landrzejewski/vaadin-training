package pl.training.shop.products.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public void removeProduct(Product product) {
        productRepository.delete(product);
    }

    public void decreaseQuantity(Long productId, int amount) {
        productRepository.decreaseQuantity(productId, amount);
    }

    public void increaseQuantity(Long productId, int amount) {
        productRepository.increaseQuantity(productId, amount);
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    public long getProductsCount() {
        return productRepository.count();
    }

}
