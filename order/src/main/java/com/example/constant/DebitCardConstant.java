package com.example.constant;

public enum DebitCardConstant {

    DEBIT_CARD_LIFECYCLE_005001("已发卡", "005001"),
    DEBIT_CARD_LIFECYCLE_005002("制卡中", "005002"),
    DEBIT_CARD_LIFECYCLE_005003("等待分配","005003"),
    DEBIT_CARD_LIFECYCLE_005004("等待制卡","005004"),
    DEBIT_CARD_LIFECYCLE_00506("销卡","00506");

    private String name;
    private String code;

    DebitCardConstant(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
