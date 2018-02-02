package com.shask.cvgenerator.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class ExperienceTranslation {
   private String language;
   private String LongDescription;
   private String position;
   private String project;
   private String shortDescription;
}
