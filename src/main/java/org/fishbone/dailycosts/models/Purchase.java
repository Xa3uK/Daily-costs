package org.fishbone.dailycosts.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_category")
    private String productCategory;
    @Column(name = "price")
    private Double price;
    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public Purchase(String productName, String productCategory, double price) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.price = price;
        this.date = new Date();
    }

    public Purchase(String productName, String productCategory, Double price, User user) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.price = price;
        this.date = new Date();
        this.user = user;
    }

    @SneakyThrows
    public String getFormattedDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
}
