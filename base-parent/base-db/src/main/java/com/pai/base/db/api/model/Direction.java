package com.pai.base.db.api.model;


/**
 * 字段排序方向。
 * <pre> 
 * 构建组：x5-base-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2013-12-22-上午11:35:40
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public enum Direction {
    ASC, DESC;
    
    
    public static Direction fromString(String value) {
        try {
            return Direction.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return ASC;
        }
    }
}

