package com.shask.cvgenerator.model;

import lombok.Data;

@Data
public class Establishment {
    private String logoUrl;
    private String name;

    public Establishment(){}
    public Establishment(String logoUrl, String name)
    {
        this.logoUrl=logoUrl;
        this.name=name;
    }
}
