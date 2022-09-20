package org.fishbone.dailycosts.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseDTO {

    @Size(min = 1, max = 50, message = "Product name length should be 1-50")
    @NotEmpty(message = "Product name should be not empty")
    private String productName;

    @NotEmpty(message = "Product category should be not empty")
    private String productCategory;

    @Pattern(regexp = "^[\\d,.]+$",
        message = "Price should contains only positive digits without spaces. In format #.##")
    @Size(min = 1, max = 6, message = "Price should be between 1-999999")
    private String price;
}
