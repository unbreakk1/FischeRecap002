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
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            boolean isFirstLine = true;

            System.out.println("Loading products from CSV file...");

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length < 6) { // Adjust for the new 'quantity' field
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String ean = fields[0];
                String productName = fields[1];
                String brand = fields[2];
                String category = fields[3];
                BigDecimal price = new BigDecimal(fields[4]);
                int quantity = Integer.parseInt(fields[5]); // New stock quantity field

                int productId = productRepo.getAllProducts().size() + 1;
                Product product = new Product(productId, productName, price, quantity);

                productRepo.addProduct(product, ean);
            }

            System.out.println("Products loaded successfully!");
        } catch (IOException e)
        {
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }
}
