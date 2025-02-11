package unbreakk1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class EANReader
{
    private final ProductRepo productRepo;

    public EANReader(ProductRepo productRepo)
    {
        this.productRepo = productRepo;
    }

    public void loadProductsFromCSV(String filePath)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            System.out.println("Loading products from CSV file...");

            while ((line = reader.readLine()) != null)
            {
                // Skip header row
                if (isFirstLine)
                {
                    isFirstLine = false;
                    continue;
                }

                // Parse product data
                String[] fields = line.split(",");
                if (fields.length < 5) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String ean = fields[0];
                String productName = fields[1];
                String brand = fields[2];
                String category = fields[3];
                BigDecimal price = new BigDecimal(fields[4]);

                // Generate unique ID for each product
                int productId = productRepo.getAllProducts().size() + 1;
                Product product = new Product(productId, productName, price);

                // Add product to the ProductRepo
                productRepo.addProduct(product, ean);
            }

            System.out.println("Products loaded successfully!");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }
}

