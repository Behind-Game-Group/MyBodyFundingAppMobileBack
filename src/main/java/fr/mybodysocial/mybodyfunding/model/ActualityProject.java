package fr.mybodysocial.mybodyfunding.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
@ToString
public class ActualityProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    @NonNull
    private Date creationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Project project;

    private String[] photosUrl;

    private String[] videosUrl;

    @OneToMany(mappedBy = "actualities", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentActualityProject> comments;
}