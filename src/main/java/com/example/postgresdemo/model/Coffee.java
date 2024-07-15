package com.example.postgresdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "coffees")
public class Coffee extends AuditModel {
    @Id
    @GeneratedValue(generator = "coffee_generator")
    @SequenceGenerator(
            name = "coffee_generator",
            sequenceName = "coffee_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private int body;

    @Column(nullable = false)
    private int acidity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public int getAcidity() {
        return acidity;
    }

    public void setAcidity(int acidity) {
        this.acidity = acidity;
    }
}
