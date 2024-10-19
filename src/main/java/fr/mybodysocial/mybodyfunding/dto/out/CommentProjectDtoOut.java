package fr.mybodysocial.mybodyfunding.dto.out;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class CommentProjectDtoOut {

    @NonNull
    private String content;
    @NonNull
    private Date dateOfPosting;
    @NonNull
    private Long idParent;
    @NonNull
    private Long IdAuthor;

}
