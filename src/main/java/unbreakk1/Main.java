package unbreakk1;

import java.math.BigDecimal;

public class Main
{
    public static void main(String[] args) {
        ProductRepo productRepo = new ProductRepo();

        productRepo.addProduct(new Product(1, "Laptop", new BigDecimal("1200.00")));
        productRepo.addProduct(new Product(2, "Smartphone", new BigDecimal("800.00")));
        productRepo.addProduct(new Product(3, "Headphones", new BigDecimal("150.00")));

        System.out.println("All Products: " + productRepo.getAllProducts());

        productRepo.getProductById(2).ifPresent(product -> System.out.println("Product Found: " + product));

        boolean isRemoved = productRepo.removeProduct(1);
        System.out.println("Product Removed: " + isRemoved);

        System.out.println("All Products After Removal: " + productRepo.getAllProducts());
    }

}