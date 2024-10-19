package fr.mybodysocial.mybodyfunding.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
@ToString
public class CommentProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idComment;
    @NonNull
    private long idParent;
    @NonNull
    private String content;
    @NonNull
    private Date dateComment;

    @ManyToOne(cascade = CascadeType.ALL)
    private AppUser author;
    @ManyToOne(cascade = CascadeType.ALL)
    private Project project;
}
