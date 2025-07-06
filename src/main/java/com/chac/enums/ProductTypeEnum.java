package com.chac.enums;

public enum ProductTypeEnum {

    /**
     * 策略模式枚举示例
     */
    HOURLY_JOB(4, "小时工", "hourlyJobServiceImpl"),
    HIRING_JOB(5, "招工信息", "hiringJobServiceImpl");

    private int code;
    private String desc;
    private String serviceClass;

    ProductTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    ProductTypeEnum(int code, String desc, String serviceClass) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public static ProductTypeEnum getEnum(int code) {
        for (ProductTypeEnum e : values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }

}
