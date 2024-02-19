package it.trew.demo.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private static final Logger logger = LoggerFactory.getLogger(Cart.class);

    private final List<CartItem> items = new ArrayList<>();
    private Discount discount;
    private double total;

    public void addItem(CartItem item) {
        logger.info("Going to add item {}", item);
        CartItem cartItem = items.stream().filter(lineItem -> lineItem.getName().equals(item.getName()))
                                 .findFirst()
                                 .orElse(null);

        if(cartItem == null) {
            items.add(item);
        } else {
            cartItem.incQuantity(item.getQuantity());
        }

        total = items.stream().mapToDouble(lineItem -> lineItem.getQuantity() * lineItem.getPrice()).sum();
    }

    public void applyDiscount(Discount discount) {
        if (this.discount == null) {
            this.discount = discount;
            switch (discount.getType()) {
                case REGULAR:
                    applyRegularDiscount(discount.getPercentage());
                case SEASONAL:
                    applySeasonalDiscount();
                    break;
                default:
                    applyDefaultDiscount();
            }
        } else {
            throw new RuntimeException("Discount is already present");
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