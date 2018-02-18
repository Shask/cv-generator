package com.shask.cvgenerator.model.parameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenerationParameters {
    private boolean isAnonymous;
    private String companyName;
    private String companyLogoUrl;
}
