package unbreakk1;

import java.math.BigDecimal;
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
        // Initialize repositories and ShopService
        this.productRepo = new ProductRepo();
        this.orderRepo = new OrderMapRepo(); // Switch to OrderListRepo if needed.
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

    private void loadEANDatabase()
    {
        System.out.println(ConsoleColors.YELLOW_BOLD + "Enter the path to your EAN CSV file:" + ConsoleColors.RESET);
        String filePath = scanner.next();
        eanReader.loadProductsFromCSV(filePath);
    }

    private void printMenu()
    {
        System.out.println(ConsoleColors.YELLOW_BOLD + "\nMain Menu:" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "1. Add Product" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "2. View All Products" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "3. Delete Product" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "4. Place Order" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "5. View All Orders" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "6. Modify Order Quantity" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "7. Search Product by EAN" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "8. Exit" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW + "Your choice: " + ConsoleColors.RESET);
    }

    private int getUserChoice()
    {
        while (!scanner.hasNextInt())
        {
            System.out.print(ConsoleColors.RED + "Please enter a valid number: " + ConsoleColors.RESET);
            scanner.next();
        }
        return scanner.nextInt();
    }

    // Add Product
    private void addProduct()
    {
        System.out.print(ConsoleColors.YELLOW + "Enter Product Name: " + ConsoleColors.RESET);
        String name = scanner.next();

        System.out.print(ConsoleColors.YELLOW + "Enter Product Price: " + ConsoleColors.RESET);
        while (!scanner.hasNextBigDecimal())
        {
            System.out.print(ConsoleColors.RED + "Please enter a valid price: " + ConsoleColors.RESET);
            scanner.next();
        }
        BigDecimal price = scanner.nextBigDecimal();

        System.out.print(ConsoleColors.YELLOW + "Enter Product EAN Code: " + ConsoleColors.RESET);
        String ean = scanner.next();

        Product product = new Product(productRepo.getAllProducts().size() + 1, name, price);
        productRepo.addProduct(product, ean);

        System.out.println(ConsoleColors.GREEN_BOLD + "Product added successfully: " + product + ConsoleColors.RESET);
    }

    // View All Products
    private void viewProducts()
    {
        System.out.println(ConsoleColors.YELLOW_BOLD + "--- All Available Products ---" + ConsoleColors.RESET);
        if (productRepo.getAllProducts().isEmpty())
            System.out.println(ConsoleColors.RED_BOLD + "No products available." + ConsoleColors.RESET);
        else
            productRepo.getAllProducts().forEach(product -> System.out.println(ConsoleColors.CYAN + product + ConsoleColors.RESET));
    }

    // Delete Product
    private void deleteProduct()
    {
        System.out.print(ConsoleColors.YELLOW + "Enter Product ID to delete: " + ConsoleColors.RESET);
        int productId = getUserChoice();

        boolean removed = productRepo.removeProduct(productId);
        if (removed)
            System.out.println(ConsoleColors.GREEN_BOLD + "Product deleted successfully." + ConsoleColors.RESET);
        else
            System.out.println(ConsoleColors.RED_BOLD + "Product not found." + ConsoleColors.RESET);

    }

    // Place an Order
    private void placeOrder()
    {
        System.out.print(ConsoleColors.YELLOW + "Enter Product ID to order: " + ConsoleColors.RESET);
        int productId = getUserChoice();

        System.out.print(ConsoleColors.YELLOW + "Enter Quantity to order: " + ConsoleColors.RESET);
        int quantity = getUserChoice();

        shopService.placeOrder(productId, quantity);
    }

    // View All Orders
    private void viewOrders()
    {
        System.out.println(ConsoleColors.YELLOW_BOLD + "--- All Orders ---" + ConsoleColors.RESET);
        if (orderRepo.getAllOrders().isEmpty())
            System.out.println(ConsoleColors.RED_BOLD + "No orders have been placed." + ConsoleColors.RESET);
        else
            orderRepo.getAllOrders().forEach(order -> System.out.println(ConsoleColors.CYAN + order + ConsoleColors.RESET));
    }

    // Modify Order Quantity
    private void modifyOrderQuantity()
    {
        System.out.print(ConsoleColors.YELLOW + "Enter Order ID to modify: " + ConsoleColors.RESET);
        int orderId = getUserChoice();

        System.out.print(ConsoleColors.YELLOW + "Enter New Quantity: " + ConsoleColors.RESET);
        int newQuantity = getUserChoice();

        boolean modified = shopService.modifyOrderQuantity(orderId, newQuantity);
        if (!modified)
            System.out.println(ConsoleColors.RED_BOLD + "Failed to modify the order quantity. Please check the Order ID." + ConsoleColors.RESET);

    }

    // Search Product by EAN
    private void searchProductByEAN()
    {
        System.out.print(ConsoleColors.YELLOW + "Enter EAN Code: " + ConsoleColors.RESET);
        String ean = scanner.next();

        Optional<Product> product = productRepo.getProductByEAN(ean);
        if (product.isPresent())
            System.out.println(ConsoleColors.GREEN_BOLD + "Product found: " + ConsoleColors.CYAN + product.get() + ConsoleColors.RESET);
        else
            System.out.println(ConsoleColors.RED_BOLD + "No product found with the given EAN." + ConsoleColors.RESET);

    }

    private void exitSystem()
    {
        System.out.println(ConsoleColors.GREEN_BOLD + "Exiting the system. Goodbye!" + ConsoleColors.RESET);
        System.exit(0);
    }
}

