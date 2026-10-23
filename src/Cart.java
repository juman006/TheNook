import java.util.ArrayList;

public class Cart {
    private ArrayList<Products> cartItems = new ArrayList<>();
    
    public void addProduct(Products product) {
        cartItems.add(product);
    }
    
    public void removeProduct(Products product) {
        cartItems.remove(product);
    }
    
    public ArrayList<Products> getCartItems() {
        return cartItems;
    }
}

