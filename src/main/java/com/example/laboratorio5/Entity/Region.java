package com.example.laboratorio5.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "regions")
public class Region {
    @Id
    @Column(name = "region_id", nullable = false, precision = 22)
    private BigDecimal id;

    @Column(name = "region_name", length = 25)
    private String regionName;

}