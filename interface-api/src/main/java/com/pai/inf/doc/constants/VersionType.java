package com.pai.inf.doc.constants;

public enum VersionType
{
    /**
     * V1.0.0版本
     */
    V100("V1.0.0"),
    
    V_DEFAULT("");
    
    private final String des;
    
    VersionType(String des)
    {
        this.des = des;
    }
    
    public String getDes()
    {
        return des;
    }
    
}
