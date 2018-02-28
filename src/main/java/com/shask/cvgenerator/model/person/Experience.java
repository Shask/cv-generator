package com.shask.cvgenerator.model.person;

import com.shask.cvgenerator.exception.LanguageNotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Log
public class Experience implements Comparable<Experience>{

    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private Establishment establishment;
    private List<ExperienceTranslation> experienceTranslations;
    private List<Technology> technologies;
    private String location;
    private String name;
    private ExperienceType type;

    public ExperienceTranslation getExperienceTranslation(String local) {
        return experienceTranslations.stream().filter(e -> local.equals(e.getLanguage())).findFirst().orElseThrow(LanguageNotFoundException::new);
    }

    @Override
    public int compareTo(final Experience o) {
        return o.dateBegin.compareTo(this.dateBegin);
    }
}
