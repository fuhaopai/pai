package com.pai.base.api.constants;


public enum ModuleType {

    MEMBER("会员"),
    TOPIC("主题"),
    ARTICLE("文章");

    private final String name;

    public String getName() {
        return name;
    }

    ModuleType(String name) {
        this.name = name;
    }
}
