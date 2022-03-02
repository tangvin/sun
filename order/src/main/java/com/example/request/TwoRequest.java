package com.example.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class TwoRequest {

//    @NotNull(message = "客户号不能为空",groups = FirstMatchWinsCompositeRule.class)
//    @Pattern(regexp = "^[0-9]{10}$",message = "客户号格式错误",groups = {SecondaryLoop.class})
    private String cusNo;

//    @NotNull(message = "有效标志不能为空",groups = FirstMatchWinsCompositeRule.class)
//    @Pattern(regexp = "^$|[0,1]$",message = "有效标志格式错误",groups = {SecondaryLoop.class})
    private String validFlag;

//    @NotNull(message = "每页条数不能为空",groups = FirstMatchWinsCompositeRule.class)
//    @Pattern(regexp = "^([1-9])$",message = "每页条数格式错误",groups = {SecondaryLoop.class})
    private String pageSize;

//    @NotNull(message = "页数不能为空",groups = FirstMatchWinsCompositeRule.class)
//    @Pattern(regexp = "^([1-9])$",message = "页数格式错误",groups = {SecondaryLoop.class})
    private String pageNo;
}
