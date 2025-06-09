import java.util.*;

class Product {
    int id;
    String name;
    double price;

    Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class CartItem {
    Product product;
    int quantity;

    CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    double getTotal() {
        return product.price * quantity;
    }
}

public class SmartBillingSystem {
    static Scanner scanner = new Scanner(System.in);
    static List<Product> productList = new ArrayList<>();
    static List<CartItem> cart = new ArrayList<>();

    public static void main(String[] args) {
        initializeProducts();
        System.out.println("=== SMART BILLING SYSTEM ===");

        boolean running = true;
        while (running) {
            try {
                displayMenu();
                int choice = getValidatedInteger("Enter your choice: ");
                handleMenu(choice);
                if (choice == 5) running = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter numbers only.");
                scanner.nextLine(); // Clear buffer
            }
        }
        System.out.println("Thank you for using Smart Billing System.");
    }

    // 1. Core Feature: Product Initialization
    static void initializeProducts() {
        productList.add(new Product(1, "Milk", 25.0));
        productList.add(new Product(2, "Bread", 20.0));
        productList.add(new Product(3, "Eggs", 6.0));
        productList.add(new Product(4, "Soap", 30.0));
        productList.add(new Product(5, "Shampoo", 275.0));
        productList.add(new Product(6, "Butter", 150.0));
        productList.add(new Product(7, "Curd", 35.0));
        productList.add(new Product(8, "Oil", 95.0));
        productList.add(new Product(9, "Facewash", 299.0));
        productList.add(new Product(10,"Moisturizer", 350.0));
    }

    // Modular Menu Display
    static void displayMenu() {
        System.out.println("\n1. Show Products");
        System.out.println("2. Add to Cart");
        System.out.println("3. Show Cart");
        System.out.println("4. Generate Bill");
        System.out.println("5. Exit");
    }

    // 4. Event Handling: Menu Choice Handling
    static void handleMenu(int choice) {
        switch (choice) {
            case 1 -> showProducts();
            case 2 -> addToCart();
            case 3 -> showCart();
            case 4 -> generateBill();
            case 5 -> {} // Exit is handled outside
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

    static void showProducts() {
        System.out.println("\nAvailable Products:");
        for (Product p : productList) {
            System.out.printf("ID: %d | Name: %-10s | Price: Rs. %.2f\n", p.id, p.name, p.price);
        }
    }

    // 2. Error Handling + 5. Data Validation + 6. Cart Merging Feature
    static void addToCart() {
        int id = getValidatedInteger("Enter product ID to add: ");
        Product selected = null;

        for (Product p : productList) {
            if (p.id == id) {
                selected = p;
                break;
            }
        }

        if (selected != null) {
            int quantity = getValidatedInteger("Enter quantity: ");
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0.");
                return;
            }

            boolean updated = false;
            for (CartItem item : cart) {
                if (item.product.id == id) {
                    item.quantity += quantity;
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                cart.add(new CartItem(selected, quantity));
            }
            System.out.println("Item added to cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    static void showCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
}

        System.out.println("\nItems in Cart:");
        double total = 0;
        for (CartItem item : cart) {
            System.out.printf("%-10s x %d = Rs. %.2f\n", item.product.name, item.quantity, item.getTotal());
            total += item.getTotal();
        }
        System.out.printf("Total so far: Rs. %.2f\n", total);
    }

    static void generateBill() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        double grandTotal = 0;
        System.out.println("\n=== FINAL BILL ===");
        for (CartItem item : cart) {
            double total = item.getTotal();
            grandTotal += total;
            System.out.printf("%-10s x %d = Rs. %.2f\n", item.product.name, item.quantity, total);
        }

        System.out.printf("TOTAL PAYABLE: Rs. %.2f\n", grandTotal);
        System.out.println("Thank you for shopping with us!");
        cart.clear();
        // 3. Integration: clear cart after billing
    }

    // 5. Data Validation Utility
    static int getValidatedInteger(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
}