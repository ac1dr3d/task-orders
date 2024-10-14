package com.task.orders.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hazelcast.partition.strategy.StringAndPartitionAwarePartitioningStrategy;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Slf4j
@Entity(name = "Order")
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "order_sequence")
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(min = 3, max = 30)
    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Size(min = 3, max = 15)
    @Column(nullable = false)
    private String status;

    @PostPersist
    public void logCreation() {
        log.info("Creating entity: " + this);
    }

    public Order(User user, String product, Integer quantity, BigDecimal price, String status) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }
}
