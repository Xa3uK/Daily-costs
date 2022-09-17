package org.fishbone.dailycosts.models;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    private Product product;
    private Date date;

    public Purchase(Product product) {
        this.product = product;
        this.date = new Date();
    }
}
