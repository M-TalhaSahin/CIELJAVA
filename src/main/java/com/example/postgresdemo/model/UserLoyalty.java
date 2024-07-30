package com.example.postgresdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "user_loyalties")
public class UserLoyalty extends AuditModel {

    @Id
    @GeneratedValue(generator = "loyalty_generator")
    @SequenceGenerator(
            name = "loyalty_generator",
            sequenceName = "loyalty_sequence",
            initialValue = 1000
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "progress_bar", nullable = false, columnDefinition = "int default 0")
    private int progressBar;

    @Column(name = "real_coffee_count", nullable = false, columnDefinition = "int default 0")
    private int realCoffeeCount;

    @Column(name = "usable_free", nullable = false, columnDefinition = "int default 0")
    private int usableFree;

    @Column(name = "used_free", nullable = false, columnDefinition = "int default 0")
    private int usedFree;

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

    public int getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(int progressBar) {
        this.progressBar = progressBar;
    }

    public int getRealCoffeeCount() {
        return realCoffeeCount;
    }

    public void setRealCoffeeCount(int realCoffeeCount) {
        this.realCoffeeCount = realCoffeeCount;
    }

    public int getUsableFree() {
        return usableFree;
    }

    public void setUsableFree(int usableFree) {
        this.usableFree = usableFree;
    }

    public int getUsedFree() {
        return usedFree;
    }

    public void setUsedFree(int usedFree) {
        this.usedFree = usedFree;
    }

}
