package com.pai.inf.doc.constants;


public enum ModuleType {

    CREDIT("额度"),
    PRODUCT("产品"),
    COOPERATE("合作机构"),
    FEE("费率");

    private final String name;

    public String getName() {
        return name;
    }

    ModuleType(String name) {
        this.name = name;
    }
}
