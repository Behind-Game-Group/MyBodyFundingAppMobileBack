package fr.mybodysocial.mybodyfunding.model;


import fr.mybodysocial.mybodyfunding.dto.in.CommentProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.InvestmentDtoIn;
import fr.mybodysocial.mybodyfunding.enums.ProjectStatus;
import fr.mybodysocial.mybodyfunding.util.Dates;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private  String title;
    @NonNull
    private String intro;
    @NonNull
    private String short_url;
    @NonNull
    private String json_url;
    @NonNull
    private String url;

    @NonNull
    private Date start_date;
    @NonNull
    private Date end_date;
    @NonNull
    private String image_large;
    @NonNull
    private String image_thumb;
    private String video;
    //currency=monnaie
    @NonNull
    private String currency;

    @NonNull
    private Float target;

    private String pledged;

    private float percent;
    private  long updates;

    private String funding_type;//par exemple "all or nothing"

    private int numberOfBackers;//bailleurs de fonds
    private long days_lefts;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;//live, successful

    private boolean enabled;
    @ManyToOne()
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToOne()
    @JoinColumn(name = "createur_id")
    private AppUser createur;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "projectsLiked")
    private List<AppUser> likedList;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Investment> contributors;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentProject> commentProjects;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ActualityProject> actualitiesProject;


    private int likes;


    //TODO : je dois rajouter la localisation
    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }


    // Ajout des méthodes like() et unlike()
    public void like(AppUser appUser) {
        this.likedList.add(appUser);
        appUser.getProjectsLiked().add(this);
        this.likes++;
    }

    public void unlike(AppUser appUser) {

        if (this.likes > 0) {
            this.likedList.remove(appUser);
            appUser.getProjectsLiked().remove(this);
            this.likes--;
        }
    }
    public CommentProject postComment(CommentProjectDtoIn content, AppUser author){
        CommentProject newComment = new CommentProject();
        newComment.setProject(this);
        newComment.setAuthor(author);
        newComment.setContent(content.getContent());
        newComment.setDateComment(new Date());
        newComment.setIdParent(content.getIdParent());

        this.commentProjects.add(newComment);
        author.getCommentsProject().add(newComment);
        return newComment;
    }
    public Investment investInProject(InvestmentDtoIn investment, AppUser contributor){
        Investment newInvestment = new Investment();
        newInvestment.setInvestmentDone(false);
        newInvestment.setDateOfInvestment(new Date());
        newInvestment.setTypeOfInvestment(investment.getTypeOfInvestment());
        newInvestment.setAmount(investment.getAmount());
        newInvestment.setProject(this);
        newInvestment.setContributors(contributor);

        return newInvestment;
    }
}
