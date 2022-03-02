package com.example.order.bean;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Order {
    private Integer id;
    private String name;
    private String address;


    private String commonId;


}