package unbreakk1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductRepo
{
    private final List<Product> products = new ArrayList<>();
    private final Map<String, Product> eanToProductMap = new HashMap<>();

    // Add a product to both the list and the EAN map
    public void addProduct(Product product, String ean)
    {
        products.add(product);
        eanToProductMap.put(ean, product);
    }

    // Remove a product by ID
    public boolean removeProduct(int id)
    {
        Optional<Product> productToRemove = getProductById(id);
        if (productToRemove.isPresent())
        {
            // Remove from list
            products.remove(productToRemove.get());
            // Remove from EAN map
            String matchedEan = eanToProductMap.entrySet().stream()
                    .filter(entry -> entry.getValue().id() == id)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);
            if (matchedEan != null)
                eanToProductMap.remove(matchedEan);

            return true;
        }
        return false;
    }

    // Get a product by ID
    public Optional<Product> getProductById(int id)
    {
        return products.stream()
                .filter(product -> product.id() == id)
                .findFirst();
    }

    // Search by EAN
    public Optional<Product> getProductByEAN(String ean)
    {
        return Optional.ofNullable(eanToProductMap.get(ean));
    }

    // Get all products
    public List<Product> getAllProducts()
    {
        return new ArrayList<>(products); // Return a copy to ensure immutability
    }
}
