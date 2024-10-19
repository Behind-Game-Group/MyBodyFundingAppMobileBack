package fr.mybodysocial.mybodyfunding.dto.in;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class ProjectDtoIn {

    @NonNull
    private Long idCreator;
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
    private String start_date;
    @NonNull
    private String end_date;
    @NonNull
    private String image_large;
    @NonNull
    private String image_thumb;
    @NonNull
    private String video;
    //currency=monnaie
    @NonNull
    private String currency;
    @NonNull
    private Float target;
    @NonNull
    private String funding_type;//par exemple "all or nothing"
}




