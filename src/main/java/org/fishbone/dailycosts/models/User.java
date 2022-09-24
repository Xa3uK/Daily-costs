package org.fishbone.dailycosts.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(min = 5, max = 50, message = "Name length must be between 5 and 50")
    private String name;

    @Size(min = 5, max = 20, message = "Login length must be between 5 and 50")
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String encodedPassword;

    @Size(min = 6, max = 15, message = "Password length must be between 6 and 15")
    private transient String pass;

    @Column(name = "balance")
    private double balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Revenue> revenueList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Purchase> purchaseList = new ArrayList<>();

    @Column(name = "role")
    private String role;

    public String getFormattedBalance() {
        return String.format("%,.2f", balance);
    }
}
