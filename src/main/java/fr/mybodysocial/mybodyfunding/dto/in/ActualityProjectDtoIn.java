package fr.mybodysocial.mybodyfunding.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
public class ActualityProjectDtoIn {

    @NonNull
    private Long idUser;

    @NonNull
    private String title;

    @NonNull
    private String content;
    private String[] photosUrl;
    private String[] videosUrl;
}
