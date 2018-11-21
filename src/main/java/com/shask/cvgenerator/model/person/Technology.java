package com.shask.cvgenerator.model.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Technology implements Comparable<Technology> {

    private String name;
    private String type;
    private String url;
    private String version;
    private boolean advertised;

    public Technology(String name,String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Technology o) {
        int res = this.type.compareTo(o.type);
        if (res == 0) {
            res = this.name.compareTo(o.name);
        }
        return res;
    }
}
