package unbreakk1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRepoTest
{

    private ProductRepo productRepo;

    @BeforeEach
    void setUp()
    {
        productRepo = new ProductRepo();
    }

    @Test
    void shouldAddAndRetrieveProducts()
    {
        Product product = new Product(1, "Laptop", new BigDecimal("1200.00"));

        // When adding a product
        productRepo.addProduct(product);

        // Then the product should be retrievable by ID
        Optional<Product> retrievedProduct = productRepo.getProductById(1);
        assertThat(retrievedProduct).isPresent();
        assertThat(retrievedProduct.get().name()).isEqualTo("Laptop");
        assertThat(retrievedProduct.get().price()).isEqualByComparingTo("1200.00");
    }

    @Test
    void shouldReturnEmptyForNonexistentProduct()
    {
        // Given no products are added

        // When trying to retrieve a nonexistent product
        Optional<Product> product = productRepo.getProductById(99);

        // Then the result should be empty
        assertThat(product).isEmpty();
    }

    @Test
    void shouldReturnAllProducts()
    {
        Product product1 = new Product(1, "Laptop", new BigDecimal("1200.00"));
        Product product2 = new Product(2, "Smartphone", new BigDecimal("800.00"));

        // When adding products
        productRepo.addProduct(product1);
        productRepo.addProduct(product2);

        // Then all products should be returned
        assertThat(productRepo.getAllProducts())
                .hasSize(2)
                .extracting(Product::name)
                .containsExactlyInAnyOrder("Laptop", "Smartphone");
    }
}