package fr.mybodysocial.mybodyfunding.dto.out;

import fr.mybodysocial.mybodyfunding.model.CommentActualityProject;
import fr.mybodysocial.mybodyfunding.model.Project;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class ActualityProjectDtoOut {

    @NonNull
    private String title;

    @NonNull
    private String content;

    private String[] photosUrl;
    private String[] videosUrl;
    @NonNull
    private Date creationDate;
    private ProjectDtoOut project;
    private List<CommentActualityProject> comments;
}