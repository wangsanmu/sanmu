package com.wangsen.user.enums;

/**
 * @author wangsen
 * @data 2018/3/25 16:22
 */
public enum LockEnum {
    LOCK_OFF("非锁定状态",0),
    LOCK_ON("锁定状态",1);

    private String name;
    private int index;

    LockEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
