package com.example.order.bean;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private String phone;

    private String commonId;

}