package fr.mybodysocial.mybodyfunding.dto.out;

import lombok.*;
import java.util.Date;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class ProjectReportedDtoOut {
    @NonNull
    private Long id;
    @NonNull
    private AppUserDtoOut user;
    @NonNull
    private ProjectDtoOut project;
    @NonNull
    private Date creationDate;
    @NonNull
    private boolean isProcessed;
    @NonNull
    private String message;
}