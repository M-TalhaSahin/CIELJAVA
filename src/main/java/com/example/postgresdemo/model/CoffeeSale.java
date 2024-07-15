package com.example.postgresdemo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coffee_sales")
public class CoffeeSale extends AuditModel {

    @Id
    @GeneratedValue(generator = "coffee_sale_generator")
    @SequenceGenerator(
            name = "coffee_sale_generator",
            sequenceName = "coffee_sale_sequence",
            initialValue = 1000
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coffee_id", nullable = false)
    private Coffee coffee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brew_id", nullable = false)
    private Brew brew;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    // Constructors, getters, and setters

    public CoffeeSale() {
        this.saleDate = LocalDateTime.now(); // Assign current timestamp by default
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public Brew getBrew() {
        return brew;
    }

    public void setBrew(Brew brew) {
        this.brew = brew;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
}
