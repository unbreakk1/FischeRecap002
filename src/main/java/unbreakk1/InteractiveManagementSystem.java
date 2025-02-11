package unbreakk1;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

public class InteractiveManagementSystem {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final ShopService shopService;
    private final Scanner scanner;

    public InteractiveManagementSystem() {
        // Initialize repositories and ShopService
        this.productRepo = new ProductRepo();
        this.orderRepo = new OrderMapRepo(); // Switch to OrderListRepo if needed.
        this.shopService = new ShopService(productRepo, orderRepo);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the Interactive Management System!");

        while (true) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> deleteProduct();
                case 4 -> placeOrder();
                case 5 -> viewOrders();
                case 6 -> modifyOrderQuantity();
                case 7 -> exitSystem();
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Add Product");
        System.out.println("2. View All Products");
        System.out.println("3. Delete Product");
        System.out.println("4. Place Order");
        System.out.println("5. View All Orders");
        System.out.println("6. Modify Order Quantity");
        System.out.println("7. Exit");
        System.out.print("Your choice: ");
    }

    private int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    // 1. Add Product
    private void addProduct() {
        System.out.print("Enter Product Name: ");
        String name = scanner.next();

        System.out.print("Enter Product Price: ");
        while (!scanner.hasNextBigDecimal())
        {
            System.out.print("Please enter a valid price: ");
            scanner.next();
        }
        BigDecimal price = scanner.nextBigDecimal();

        Product product = new Product(productRepo.getAllProducts().size() + 1, name, price);
        productRepo.addProduct(product);

        System.out.println("Product added successfully: " + product);
    }

    // 2. View All Products
    private void viewProducts()
    {
        System.out.println("--- All Available Products ---");
        productRepo.getAllProducts().forEach(System.out::println);
    }

    // 3. Delete Product
    private void deleteProduct()
    {
        System.out.print("Enter Product ID to delete: ");
        int productId = getUserChoice();

        boolean removed = productRepo.removeProduct(productId);
        if (removed)
            System.out.println("Product deleted successfully.");
         else
            System.out.println("Product not found.");
    }

    // 4. Place an Order
    private void placeOrder()
    {
        System.out.print("Enter Product ID to order: ");
        int productId = getUserChoice();

        System.out.print("Enter Quantity to order: ");
        int quantity = getUserChoice();

        shopService.placeOrder(productId, quantity);
    }

    // 5. View All Orders
    private void viewOrders()
    {
        System.out.println("--- All Orders ---");
        if (orderRepo.getAllOrders().isEmpty())
            System.out.println("No orders have been placed.");
         else
            orderRepo.getAllOrders().forEach(System.out::println);
    }

    // 6. Modify Order Quantity
    private void modifyOrderQuantity()
    {
        System.out.print("Enter Order ID to modify: ");
        int orderId = getUserChoice();

        System.out.print("Enter New Quantity: ");
        int newQuantity = getUserChoice();

        boolean modified = shopService.modifyOrderQuantity(orderId, newQuantity);
        if (!modified)
            System.out.println("Failed to modify the order quantity. Please check the Order ID.");

    }

    // 7. Exit the system
    private void exitSystem()
    {
        System.out.println("Exiting the system. Goodbye!");
        System.exit(0);
    }

    // Main method to run the interactive system
  //  public static void main(String[] args)
  //  {
  //      InteractiveManagementSystem system = new InteractiveManagementSystem();
  //      system.start();
  //  }
}
