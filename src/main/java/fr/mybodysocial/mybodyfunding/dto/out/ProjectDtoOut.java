package fr.mybodysocial.mybodyfunding.dto.out;

import fr.mybodysocial.mybodyfunding.enums.ProjectStatus;
import fr.mybodysocial.mybodyfunding.model.AppUser;
import lombok.*;

import java.util.Date;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ProjectDtoOut {

    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String intro;
    @NonNull
    private String short_url;
    @NonNull
    private String json_url;
    @NonNull
    private String url;

    @NonNull
    private Date start_date;
    @NonNull
    private Date end_date;
    @NonNull
    private String image_large;
    @NonNull
    private String image_thumb;
    private String video;
    //currency=monnaie
    @NonNull
    private String currency;

    @NonNull
    private Float target;

    private String pledged;

    private float percent;
    private long updates;

    private String funding_type;

    private int numberOfBackers;

    private ProjectStatus status;

    private AppUserDtoOut createur;

    private Long projectId;
}
