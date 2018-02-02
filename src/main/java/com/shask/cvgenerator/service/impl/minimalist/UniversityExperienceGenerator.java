package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.layout.element.BlockElement;
import com.shask.cvgenerator.model.Experience;
import com.shask.cvgenerator.model.ExperienceType;
import com.shask.cvgenerator.model.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class UniversityExperienceGenerator extends GeneralExperienceGenerator implements BlockElementGenerator {

    @Override
    public BlockElement generateFor(final Person person) {

        List<Experience> universityExperience =
            person.getExperiences().stream().filter(e -> e.getType() == ExperienceType.University).collect(Collectors.toList());
        return experienceListElement(universityExperience);
    }
}
