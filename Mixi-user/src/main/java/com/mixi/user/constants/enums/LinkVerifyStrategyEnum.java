package com.mixi.user.constants.enums;

public enum LinkVerifyStrategyEnum {
    FIRST("1", "LoginLinkVerifyStrategy"),
    SECOND("2", "RegisterLinkVerifyStrategy");

    private final String k;
    private final String v;

    LinkVerifyStrategyEnum(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public static String getSecondValue(String k) {
        for (LinkVerifyStrategyEnum strategy : LinkVerifyStrategyEnum.values()) {
            if (strategy.k.equals(k)) {
                return strategy.v;
            }
        }
        throw new IllegalArgumentException("No matching strategy found for firstValue: " + k);
    }
}
