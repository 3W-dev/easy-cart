package it.trew.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Discount {
    private DiscountType type;
    private Double percentage;
}
