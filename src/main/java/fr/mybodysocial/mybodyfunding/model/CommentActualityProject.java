package fr.mybodysocial.mybodyfunding.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
@ToString
public class CommentActualityProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String content;

    @NonNull
    private Date postDate;

    @NonNull
    private AppUser postedBy;

    @ManyToOne(cascade = CascadeType.ALL)
    private ActualityProject actualities;

}