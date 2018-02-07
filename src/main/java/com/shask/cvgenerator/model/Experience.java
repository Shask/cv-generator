package com.shask.cvgenerator.model;

import com.shask.cvgenerator.exception.LanguageNotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Log
public class Experience {

    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private Establishment establishment;
    private List<ExperienceTranslation> experienceTranslation;
    private List<Technology> technologies;
    private String location;
    private String name;
    private ExperienceType type;

    public ExperienceTranslation getExperienceTranslation(String local) {
        return experienceTranslation.stream().filter(e -> local.equals(e.getLanguage())).findFirst().orElseThrow(LanguageNotFoundException::new);
    }
}