package fr.mybodysocial.mybodyfunding.dto.in;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class CategorieDtoIn {

    @NonNull
    private String title;
    @NonNull
    private String imageUrl;
}
