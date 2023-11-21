package com.example.accommodationbookingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "books")
@Data
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Type type;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "address_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Address adress;
    private String size;
    private String amenities;
    private BigDecimal dailyRate;
    private Integer availability;
    private enum Type {
        HOUSE,
        APARTMENT,
        HOTEL
    }
}
