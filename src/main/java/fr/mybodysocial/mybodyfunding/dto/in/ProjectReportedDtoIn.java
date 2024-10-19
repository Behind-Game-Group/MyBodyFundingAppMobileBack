package fr.mybodysocial.mybodyfunding.dto.in;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor(force = true)
public class ProjectReportedDtoIn {
    @NonNull
    private Long userId;
    @NonNull
    private String message;
}