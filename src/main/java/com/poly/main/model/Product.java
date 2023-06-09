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
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "Products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotEmpty(message = "{NotEmpty.Product.name}")
    private String name;
    
    private String image;
    
    private String description;
    @NotNull(message = "{NotNull.Product.price}")
    @DecimalMin(value = "0.00", inclusive = false, message = "{DecimalMin.Product.price}")
    private BigDecimal price;

    @NotNull(message = "{NotEmpty.Product.category}")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



    // Constructors, getters, and setters
}
