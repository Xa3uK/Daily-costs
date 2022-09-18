package org.fishbone.dailycosts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseDTO {

    private String productName;
    private String productCategory;
    private String price;
}
