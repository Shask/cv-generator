package com.shask.cvgenerator.model.person;

import lombok.Data;

@Data
public class Language {
    String name;
    String level;

    public Language(final String name, final String level) {
        this.name = name;
        this.level = level;
    }

    public Language() {
    }
}
