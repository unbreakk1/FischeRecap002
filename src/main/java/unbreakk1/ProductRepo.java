package unbreakk1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepo
{
    private final List<Product> products = new ArrayList<>();

    // Method to add a product
    public void addProduct(Product product)
    {
        products.add(product);
    }

    // Method to remove a product by ID
    public boolean removeProduct(int id)
    {
        return products.removeIf(product -> product.id() == id);
    }

    // Method to get a product by ID
    public Optional<Product> getProductById(int id)
    {
        return products.stream()
                .filter(product -> product.id() == id)
                .findFirst();
    }

    // Method to get all products
    public List<Product> getAllProducts()
    {
        return new ArrayList<>(products); // Return a copy to ensure immutability
    }

}
