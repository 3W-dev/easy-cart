package it.trew.demo.model;

import lombok.Data;

@Data
public class Discount {
    private DiscountType type;
    private double percentage;
}
