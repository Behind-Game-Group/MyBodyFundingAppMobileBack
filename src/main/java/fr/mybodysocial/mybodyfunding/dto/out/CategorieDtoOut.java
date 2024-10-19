package fr.mybodysocial.mybodyfunding.dto.out;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class CategorieDtoOut {

    @NonNull
    private long id;

    @NonNull
    private String title;
    @NonNull
    private String imageUrl;
}