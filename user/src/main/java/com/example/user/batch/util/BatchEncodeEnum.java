package com.example.user.batch.util;

/**
 * @description 枚举类：编码格式
 * @author TY
 * @date 2021/8/28 9:18
 */
public enum BatchEncodeEnum {

    BATCH_ENCODE_ENUM_UTF_8("UTF-8"),
    BATCH_ENCODE_ENUM_GBK("GBK"),
    BATCH_ENCODE_ENUM_GB2312("GB2312");

    private String encoding;

    BatchEncodeEnum(String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
