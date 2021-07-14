package com.example.tacocloud.web;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@ConfigurationProperties(prefix="taco.orders")
@Component
@Data
@Validated
public class OrderProp {
    @Min(value=5, message="Must be between 5 and 25")
    @Max(value=25, message="Must be between 5 and 25")
    private int pageSize = 20;
}
