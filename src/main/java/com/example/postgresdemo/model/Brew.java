package com.example.postgresdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "brews")
public class Brew extends AuditModel {
    @Id
    @GeneratedValue(generator = "brew_generator")
    @SequenceGenerator(
            name = "brew_generator",
            sequenceName = "brew_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    // Additional fields as needed

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
}
