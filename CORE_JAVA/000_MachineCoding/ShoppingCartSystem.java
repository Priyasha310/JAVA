import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class Product {
    private final int productId;
    private final String productName;
    private final BigDecimal price;

    public Product(int productId, String productName, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public BigDecimal getPrice() { return price; }
}

class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void updateQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getItemTotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}

class ShoppingCart {
    private final List<CartItem> cartItems = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero");

        CartItem existingItem = findItem(product.getProductId());
        if (existingItem != null) {
            existingItem.updateQuantity(existingItem.getQuantity() + quantity);
        } else {
            cartItems.add(new CartItem(product, quantity));
        }
    }

    public void removeProduct(int productId) {
        CartItem item = findItem(productId);
        if (item == null) throw new IllegalArgumentException("Product not found in cart: " + productId);
        cartItems.remove(item);
    }

    public void updateQuantity(int productId, int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");

        CartItem item = findItem(productId);
        if (item == null) throw new IllegalArgumentException("Product not found in cart: " + productId);

        if (quantity == 0) cartItems.remove(item);
        else item.updateQuantity(quantity);
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) total = total.add(item.getItemTotal());
        return total;
    }

    public void displayCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Shopping cart is empty.");
            return;
        }

        System.out.println("\nShopping Cart");
        System.out.println("---------------------------------------------");
        System.out.printf("%-5s %-15s %-12s %-10s%n", "ID", "Product", "Price", "Quantity");
        System.out.println("---------------------------------------------");

        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            System.out.printf("%-5d %-15s %-12s %-10d%n",
                    product.getProductId(), product.getProductName(),
                    product.getPrice(), item.getQuantity());
        }

        System.out.println("---------------------------------------------");
        System.out.println("Cart Total: " + calculateTotal());
    }

    private CartItem findItem(int productId) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == productId) return item;
        }
        return null;
    }
}

public class ShoppingCartSystem {
    public static void main(String[] args) {
        Product laptop = new Product(1, "Laptop", new BigDecimal("50000"));
        Product mouse = new Product(2, "Mouse", new BigDecimal("1000"));
        Product keyboard = new Product(3, "Keyboard", new BigDecimal("2000"));

        ShoppingCart cart = new ShoppingCart();

        cart.addProduct(laptop, 1);
        cart.addProduct(mouse, 2);
        cart.displayCart();

        System.out.println("\nAdding another laptop...");
        cart.addProduct(laptop, 1);
        cart.displayCart();

        System.out.println("\nUpdating mouse quantity...");
        cart.updateQuantity(2, 3);
        cart.displayCart();

        System.out.println("\nAdding keyboard...");
        cart.addProduct(keyboard, 1);
        cart.displayCart();

        System.out.println("\nRemoving keyboard...");
        cart.removeProduct(3);
        cart.displayCart();

        System.out.println("\nFinal Cart Total: " + cart.calculateTotal());
    }
}