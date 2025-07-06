package com.chac.enums;

public enum FilterFailReasonEnum {
    //平台黑名单
    PLATFORM_BLACKLIST(1, "平台黑名单", "platformBlacklist", "平台已禁止您接单"),
    //实名要求
    REAL_NAME_REQUIREMENT(2, "实名要求", "idVerifyRequirement", "抱歉，您还未完成实名认证，请先完成实名认证再报名订阅"),
    //与已抢班次、已接订单期望上门时间否时间冲突
    TIME_CONFLICT(3, "时间冲突", "timeConflict", "报名时间冲突，您当前已有待服务订单。"),
    //同一企业同一天工时是否大于4小时 4还是24 是可配置的，读阿波罗 SchedulingWork_MaxDuration
    SAME_COMPANY_WORK_HOURS_LIMIT(4, "同一企业同一天工时大于24小时", "sameCompanyWorkHoursLimit", "报名时间冲突，相同企业，超过最大可接单时间。"),
    //服务者年龄段限制
    SERVICE_AGE_LIMIT(5, "服务者年龄段限制", "serviceAgeLimit", "感谢您对有活平台的关注和支持！根据平台及相关法规规定，平台接单人年龄范围为18-60周岁。由于年龄限制，我们暂时无法为您提供服务。感谢您的理解，祝生活愉快～"),
    //全职校验（按照人标签）
    FULL_TIME_CHECK(6, "全职校验（按照人标签）", "fullTimeCheck", "抱歉，操作失败。您属于全职服务人员账号，请等待平台指派订单！"),
    //订单性别要求
    ORDER_SEX_REQUIREMENT(7, "订单性别要求", "orderSexRequirement", "您不符合报名条件，详见用工要求"),
    //订单年龄要求
    ORDER_AGE_REQUIREMENT(8, "订单年龄要求", "orderAgeRequirement", "您不符合报名条件，详见用工要求"),
    INTERNAL_ERROR(-1, "服务异常", "internalError", "服务异常"),
    INVALID_WORKING_SCHEDULE_ID(-2, "无效的班次ID", "invalidWorkingSchedule", "排班已失效");


    private final int code;

    private final String desc;

    private final String ruleFieldName;

    private final String returnTips;

    FilterFailReasonEnum(int code, String desc, String ruleFieldName, String returnTips) {
        this.code = code;
        this.desc = desc;
        this.ruleFieldName = ruleFieldName;
        this.returnTips = returnTips;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getRuleFieldName() {
        return ruleFieldName;
    }

    public String getReturnTips() {
        return returnTips;
    }

    /**
     * 根据fieldName找到code
     */
    public static int getCodeByFieldName(String fieldName) {
        for (FilterFailReasonEnum reason : FilterFailReasonEnum.values()) {
            if (reason.getRuleFieldName().equals(fieldName)) {
                return reason.getCode();
            }
        }
        return 0; // 默认返回0或其他适当的值
    }

    //根据fieldName找到枚举
    public static FilterFailReasonEnum getByFieldName(String fieldName) {
        for (FilterFailReasonEnum reason : FilterFailReasonEnum.values()) {
            if (reason.getRuleFieldName().equals(fieldName)) {
                return reason;
            }
        }
        return null; // 如果没有找到，返回null
    }


}
