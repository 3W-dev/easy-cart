package it.trew.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartUnitTest {
    @Test
    void applySeasonalDiscount() {
        Cart cart = new Cart();
        cart.addItem(new CartItem("Item1", 100.0, 1));
        cart.addItem(new CartItem("Item2", 50.0, 2));

        Discount seasonalDiscount = new Discount();
        seasonalDiscount.setType(DiscountType.SEASONAL);
        cart.applyDiscount(seasonalDiscount);

        // Verify
        double expectedTotal = 180.0; // 10% off of 200
        assertEquals(expectedTotal, cart.getTotal(), "Seasonal discount should be applied correctly");
    }
}
