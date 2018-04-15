package com.wangsen.user.config.druid;

/**
 * @author wangsen
 * @data 2018/4/14 19:26
 */
public enum DBTypeEnum {
    master("master"),
    slave("slave");

    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
