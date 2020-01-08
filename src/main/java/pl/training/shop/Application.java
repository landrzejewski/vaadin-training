package pl.training.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductCategory;
import pl.training.shop.products.model.ProductService;

import java.util.Date;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Autowired
    public Application(ProductService productService) {
        productService.addProductCategory(new ProductCategory("Warzywa"));
        productService.addProductCategory(new ProductCategory("Owoce"));

        productService.addProduct(Product.builder()
                .name("Pietruszka")
                .description("Dobra, zdrowa, świeża")
                .quantity(10)
                .price(100)
                .availablleSince(new Date())
                .build());
        productService.addProduct(Product.builder()
                .name("Brukselka")
                .description("Pyszna, zielona, krajowa")
                .quantity(100)
                .price(50)
                .availablleSince(new Date())
                .build());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
