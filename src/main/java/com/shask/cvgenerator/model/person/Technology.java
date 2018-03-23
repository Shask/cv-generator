package com.shask.cvgenerator.model.person;

import lombok.*;

@Data
@Builder
public class Technology {

    private String name;
    private String type;
    private String url;
    private String version;
    private boolean advertised;

    public Technology(String name) {
        this.name = name;
    }

    public Technology() {
    }

    public Technology(final String name, final String type, final String url, final String version, final boolean advertised) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.version = version;
        this.advertised = advertised;
    }

    @Override
    public String toString() {
        return name;
    }
}
