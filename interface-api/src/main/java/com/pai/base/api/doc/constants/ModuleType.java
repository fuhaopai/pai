package com.pai.base.api.doc.constants;


public enum ModuleType {

    MEMBER("额度"),
    TOPIC("产品");

    private final String name;

    public String getName() {
        return name;
    }

    ModuleType(String name) {
        this.name = name;
    }
}
