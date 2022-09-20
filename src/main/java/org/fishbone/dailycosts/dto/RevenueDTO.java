package org.fishbone.dailycosts.dto;

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
public class RevenueDTO {

    @Pattern(regexp = "^[1-9]{1,10}$", message = "Price should contains only positive digits without spaces")
    @Size(min = 1, max = 6, message = "Amount should be between 1-999999")
    private String amount;
    private String revenueType;
}
