package com.poly.main.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Discounts")
public class Discount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "{NotEmpty.Discount.product}")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @NotNull(message = "{NotNull.Discount.discountAmount}")
    @DecimalMin(value = "0.00", inclusive = false, message = "{DecimalMin.Discount.discountAmount}")
    private BigDecimal discountAmount;

    // Constructors, getters, and setters
}

