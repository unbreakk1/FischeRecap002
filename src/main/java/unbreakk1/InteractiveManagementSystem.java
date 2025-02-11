package unbreakk1;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class InteractiveManagementSystem
{
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final ShopService shopService;
    private final EANReader eanReader;
    private final Scanner scanner;

    public InteractiveManagementSystem()
    {
        this.productRepo = new ProductRepo();
        this.orderRepo = new OrderMapRepo();
        this.shopService = new ShopService(productRepo, orderRepo);
        this.eanReader = new EANReader(productRepo);
        this.scanner = new Scanner(System.in);
    }

    public void start()
    {
        System.out.println(ConsoleColors.CYAN_BOLD + "Welcome to the Interactive Management System!" + ConsoleColors.RESET);
        loadEANDatabase();

        while (true)
        {
            printMenu();
            int choice = getUserChoice();

            switch (choice)
            {
                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> deleteProduct();
                case 4 -> placeOrder();
                case 5 -> viewOrders();
                case 6 -> modifyOrderQuantity();
                case 7 -> searchProductByEAN();
                case 8 -> exitSystem();
                default -> System.out.println(ConsoleColors.RED + "Invalid choice! Please try again." + ConsoleColors.RESET);
            }
        }
    }

    private void deleteProduct()
    {
        System.out.print(ConsoleColors.YELLOW + "Enter Product ID to delete: " + ConsoleColors.RESET);
        int productId = getUserChoice();

        boolean removed = productRepo.removeProduct(productId);
        if (removed)
            System.out.println(ConsoleColors.GREEN_BOLD + "Product deleted successfully!" + ConsoleColors.RESET);
        else
            System.out.println(ConsoleColors.RED_BOLD + "Product not found!" + ConsoleColors.RESET);

    }

    private void viewOrders()
    {
        System.out.println(ConsoleColors.YELLOW_BOLD + "Order List:" + ConsoleColors.RESET);
        List<Order> orders = orderRepo.getAllOrders();

        if (orders.isEmpty())
            System.out.println(ConsoleColors.RED_BOLD + "No orders found!" + ConsoleColors.RESET);
        else
            for (Order order : orders)
            {
                System.out.println(ConsoleColors.GREEN + order + ConsoleColors.RESET);
            }

    }

    private void modifyOrderQuantity()
    {
        System.out.print(ConsoleColors.YELLOW + "Enter Order ID to modify: " + ConsoleColors.RESET);
        int orderId = getUserChoice();

        System.out.print(ConsoleColors.YELLOW + "Enter new quantity: " + ConsoleColors.RESET);
        while (!scanner.hasNextInt())
        {
            System.out.print(ConsoleColors.RED_BOLD + "Please enter a valid quantity: " + ConsoleColors.RESET);
            scanner.next();
        }
        int newQuantity = scanner.nextInt();

        boolean modified = orderRepo.modifyOrderQuantity(orderId, newQuantity);
        if (modified)
            System.out.println(ConsoleColors.GREEN_BOLD + "Order quantity updated successfully!" + ConsoleColors.RESET);
        else
            System.out.println(ConsoleColors.RED_BOLD + "Order not found!" + ConsoleColors.RESET);

    }

    private void searchProductByEAN()
    {
        System.out.print(ConsoleColors.YELLOW + "Enter Product EAN: " + ConsoleColors.RESET);
        String ean = scanner.next();

        Optional<Product> product = productRepo.getProductByEAN(ean);
        if (product.isPresent())
            System.out.println(ConsoleColors.GREEN_BOLD + "Product Found: " + product.get() + ConsoleColors.RESET);
        else
            System.out.println(ConsoleColors.RED_BOLD + "No product found with the given EAN!" + ConsoleColors.RESET);

    }


    private void loadEANDatabase()
    {
        System.out.println(ConsoleColors.YELLOW_BOLD + "Enter the path to your EAN CSV file:" + ConsoleColors.RESET);
        String filePath = scanner.next();
        try {
            eanReader.loadProductsFromCSV(filePath); // Ensure `eanReader` is properly initialized
            System.out.println(ConsoleColors.GREEN_BOLD + "EAN database loaded successfully!" + ConsoleColors.RESET);
        } catch (Exception e)
        {
            System.out.println(ConsoleColors.RED_BOLD + "Failed to load EAN database. Please check the file path and format!" + ConsoleColors.RESET);
        }
    }

    private void printMenu()
    {
        System.out.println(ConsoleColors.YELLOW_BOLD + "\nMain Menu:" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD + "1. Add Product" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD + "2. View All Products" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD + "3. Delete Product" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD + "4. Place Order" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD + "5. View All Orders" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD + "6. Modify Order Quantity" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD + "7. Search Product by EAN" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD + "8. Exit" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD + "Your choice: " + ConsoleColors.RESET);
    }

    private int getUserChoice()
    {
        while (!scanner.hasNextInt())
        {
            System.out.print(ConsoleColors.RED_BOLD + "Please enter a valid number: " + ConsoleColors.RESET);
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void addProduct()
    {
        System.out.print(ConsoleColors.YELLOW + "Enter Product Name: " + ConsoleColors.RESET);
        String name = scanner.next();

        System.out.print(ConsoleColors.YELLOW + "Enter Product Price: " + ConsoleColors.RESET);
        while (!scanner.hasNextBigDecimal())
        {
            System.out.print(ConsoleColors.RED_BOLD + "Please enter a valid price: " + ConsoleColors.RESET);
            scanner.next();
        }
        BigDecimal price = scanner.nextBigDecimal();

        System.out.print(ConsoleColors.YELLOW + "Enter Product Quantity: " + ConsoleColors.RESET);
        while (!scanner.hasNextInt())
        {
            System.out.print(ConsoleColors.RED_BOLD + "Please enter a valid quantity: " + ConsoleColors.RESET);
            scanner.next();
        }
        int quantity = scanner.nextInt();

        System.out.print(ConsoleColors.YELLOW + "Enter Product EAN Code: " + ConsoleColors.RESET);
        String ean = scanner.next();

        // Create the product and add it to the repository
        Product product = new Product(productRepo.getAllProducts().size() + 1, name, price, quantity);
        productRepo.addProduct(product, ean);

        System.out.println(ConsoleColors.GREEN_BOLD + "Product added successfully: " + product + ConsoleColors.RESET);
    }


    private void placeOrder() {
        System.out.print(ConsoleColors.YELLOW + "Enter Product ID to order: " + ConsoleColors.RESET);
        int productId = getUserChoice();

        Optional<Product> productOptional = productRepo.getProductById(productId);
        if (productOptional.isEmpty())
        {
            System.out.println(ConsoleColors.RED_BOLD + "Product not found!" + ConsoleColors.RESET);
            return;
        }

        Product product = productOptional.get();
        if (product.quantity() <= 0)
        {
            System.out.println(ConsoleColors.RED_BOLD + "Product out of stock! Cannot place order." + ConsoleColors.RESET);
            return;
        }

        System.out.print(ConsoleColors.YELLOW + "Enter Quantity to order: " + ConsoleColors.RESET);
        int quantity = getUserChoice();

        if (product.quantity() < quantity)
        {
            System.out.println(ConsoleColors.RED_BOLD + "Not enough stock available. Current stock: " + product.quantity() + ConsoleColors.RESET);
            return;
        }

        // Update stock and place order
        productRepo.updateProductStock(productId, quantity);
        shopService.placeOrder(productId, quantity);

        System.out.println(ConsoleColors.GREEN_BOLD + "Order placed successfully for " + quantity + " unit(s) of " + product.name() + ConsoleColors.RESET);
    }

    private void viewProducts()
    {
        System.out.println(ConsoleColors.YELLOW_BOLD + "--- All Available Products ---" + ConsoleColors.RESET);
        if (productRepo.getAllProducts().isEmpty())
            System.out.println(ConsoleColors.RED_BOLD + "No products available." + ConsoleColors.RESET);
        else
            productRepo.getAllProducts().forEach(product -> System.out.println(
                    ConsoleColors.CYAN + product +
                            " | Stock: " + product.quantity() + ConsoleColors.RESET
            ));

    }

    private void exitSystem()
    {
        System.out.println(ConsoleColors.GREEN_BOLD + "Exiting the system. Goodbye!" + ConsoleColors.RESET);
        System.exit(0); // Terminates the program
    }

}