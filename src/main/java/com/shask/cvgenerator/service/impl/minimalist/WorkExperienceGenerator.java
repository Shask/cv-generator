package com.shask.cvgenerator.service.impl.minimalist;

import com.itextpdf.layout.element.BlockElement;
import com.shask.cvgenerator.model.person.Experience;
import com.shask.cvgenerator.model.person.ExperienceType;
import com.shask.cvgenerator.model.person.Person;
import com.shask.cvgenerator.service.BlockElementGenerator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WorkExperienceGenerator extends GeneralExperienceGenerator implements BlockElementGenerator{

    @Override
    public BlockElement generateFor(final Person person) {
        Objects.requireNonNull(person);
        List<Experience> universityExperience = person.getExperiences()
            .stream().filter(e -> e.getType() == ExperienceType.Work || e.getType() == ExperienceType.Internship)
            .collect(Collectors.toList());
        return experienceListElement(universityExperience,1);
    }

}
