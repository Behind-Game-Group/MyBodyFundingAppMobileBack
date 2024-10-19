package fr.mybodysocial.mybodyfunding.dto.in;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
public class CommentActualityProjectDtoIn {
    @NonNull
    private String content;

    @NonNull
    private long idUserWhoPosted;

    @NonNull
    private long idActuality;
}
