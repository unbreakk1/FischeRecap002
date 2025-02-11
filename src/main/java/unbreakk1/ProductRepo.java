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

    public void addProduct(Product product, String ean)
    {
        products.add(product);
        eanToProductMap.put(ean, product);
    }

    public boolean removeProduct(int id)
    {
        Optional<Product> productToRemove = getProductById(id);
        if (productToRemove.isPresent())
        {
            products.remove(productToRemove.get());
            eanToProductMap.entrySet().removeIf(entry -> entry.getValue().id() == id);
            return true;
        }
        return false;
    }

    public Optional<Product> getProductById(int id)
    {
        return products.stream()
                .filter(product -> product.id() == id)
                .findFirst();
    }

    public Optional<Product> getProductByEAN(String ean)
    {
        return Optional.ofNullable(eanToProductMap.get(ean));
    }

    public List<Product> getAllProducts()
    {
        return new ArrayList<>(products);
    }

    public void updateProductStock(int productId, int quantityOrdered)
    {
        getProductById(productId).ifPresent(originalProduct ->
        {
            // Update the product's stock
            Product updatedProduct = originalProduct.decreaseQuantity(quantityOrdered);

            // Update both the product list and the EAN map
            products.set(products.indexOf(originalProduct), updatedProduct);
            eanToProductMap.replace(
                    eanToProductMap.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue().id() == productId)
                            .map(Map.Entry::getKey)
                            .findFirst()
                            .orElse(null),
                    updatedProduct
            );
        });
    }
}
