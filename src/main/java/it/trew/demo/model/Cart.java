package it.trew.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();
    private double total;

    public void addItem(CartItem item) {
        items.add(item);
        total += item.getPrice() * item.getQuantity();
    }

    public double getTotal() {
        return total;
    }

    public void applyDiscount(Discount discount) {
        switch (discount.getType()) {
            case REGULAR:
                applyRegularDiscount(discount.getPercentage());
            case SEASONAL:
                applySeasonalDiscount();
                break;
            default:
                applyDefaultDiscount();
        }
    }

    private void applySeasonalDiscount() {
        double discountAmount = this.total * 0.10;
        this.total -= discountAmount;
    }

    private void applyRegularDiscount(double discount) {
        double discountAmount = this.total * (discount / 100.0);
        this.total -= discountAmount;
    }

    private void applyDefaultDiscount() {
        double discountAmount = this.total * 0.05;
        this.total += discountAmount;
    }
}
