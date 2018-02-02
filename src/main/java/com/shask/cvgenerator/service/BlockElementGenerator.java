package com.shask.cvgenerator.service;

import com.itextpdf.layout.element.BlockElement;
import com.shask.cvgenerator.model.Person;

@FunctionalInterface
public interface BlockElementGenerator {
    BlockElement generateFor(Person person);
}
