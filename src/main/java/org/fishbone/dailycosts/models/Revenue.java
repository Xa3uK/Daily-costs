package org.fishbone.dailycosts.models;

import java.util.Date;
import liquibase.pro.packaged.A;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Revenue {
    private double amount;
    private String revenueType;
    private Date date;

    public Revenue(double amount, String revenueType) {
        this.amount = amount;
        this.revenueType = revenueType;
        this.date = new Date();
    }
}
