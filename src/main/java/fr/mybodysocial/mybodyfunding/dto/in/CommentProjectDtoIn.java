package fr.mybodysocial.mybodyfunding.dto.in;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class CommentProjectDtoIn {

    @NonNull
    private long idAuthor;

    @NonNull
    private long idParent;
    @NonNull
    private String content;
}
