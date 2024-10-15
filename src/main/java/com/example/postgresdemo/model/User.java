package com.example.postgresdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends AuditModel {
  @Id
  @GeneratedValue(generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", initialValue = 1000)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String fullname;

  @Column(nullable = false)
  private boolean statustype = true;

  @Column(nullable = false)
  private String provider; // Enum yerine String olarak tanımlandı

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public boolean getStatusType() {
    return statustype;
  }

  public void setStatusType(boolean statustype) {
    this.statustype = statustype;
  }

  public String getProvider() { // Geri dönüş tipi String olarak değiştirildi
    return provider;
  }

  public void setProvider(String provider) { // Parametre tipi String olarak değiştirildi
    this.provider = provider;
  }
}