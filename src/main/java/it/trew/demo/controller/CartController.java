package it.trew.demo.controller;

import it.trew.demo.model.Cart;
import it.trew.demo.model.CartItem;
import it.trew.demo.model.Discount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/cart")
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private static final Map<Long, Cart> carts = new HashMap<>();

    @PostMapping("/addItem/{sessionId}")
    public String addItemToCart(@PathVariable Long sessionId, @RequestBody CartItem item) {
        getCart(sessionId).addItem(item);
        logger.info("carts: {}", carts);
        return "Item added to cart";
    }

    @PostMapping("/applyDiscount/{sessionId}")
    public String applyDiscount(@PathVariable Long sessionId, @RequestBody Discount discount) {
        getCart(sessionId).applyDiscount(discount);
        return "Discount applied";
    }

    @GetMapping("/total/{sessionId}")
    public double getCartTotal(@PathVariable Long sessionId) {
        return getCart(sessionId).getTotal();
    }

    @DeleteMapping("/clear/{sessionId}")
    public void clear(@PathVariable Long sessionId) {
        carts.remove(sessionId);
    }

    @GetMapping("/listSessions")
    public Set<Long> listSessions() {
        return carts.keySet();
    }

    private Cart getCart(Long sessionId) {
        logger.info("Going to get cart for sessionId: {}", sessionId);
        Cart cart = carts.get(sessionId);
        if(cart == null) {
            cart = new Cart();
            carts.put(sessionId, cart);
        }
        return cart;
    }
}