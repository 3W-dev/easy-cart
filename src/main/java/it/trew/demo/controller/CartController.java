package it.trew.demo.controller;

import it.trew.demo.model.Cart;
import it.trew.demo.model.CartItem;
import it.trew.demo.model.Discount;
import it.trew.demo.model.Outcome;
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
    public CartItem addItemToCart(@PathVariable Long sessionId, @RequestBody CartItem item) {
        Cart cart = getCart(sessionId);
        cart.addItem(item);
        logger.info("cart: {}", cart);
        return item;
    }

    @PostMapping("/applyDiscount/{sessionId}")
    public Outcome applyDiscount(@PathVariable Long sessionId, @RequestBody Discount discount) {
        Outcome result = new Outcome("discount applied");
        try {
            getCart(sessionId).applyDiscount(discount);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @GetMapping("/total/{sessionId}")
    public double getCartTotal(@PathVariable Long sessionId) {
        return getCart(sessionId).getTotal();
    }

    @DeleteMapping("/clear/{sessionId}")
    public void clear(@PathVariable Long sessionId) {
        carts.remove(sessionId);
    }

    @GetMapping("/{sessionId}")
    public Cart list(@PathVariable Long sessionId) {
        return getCart(sessionId);
    }

    @GetMapping("/sessions")
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