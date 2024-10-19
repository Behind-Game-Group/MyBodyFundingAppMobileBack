package fr.mybodysocial.mybodyfunding.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.mybodysocial.mybodyfunding.enums.AppUserRole;
import fr.mybodysocial.mybodyfunding.enums.Genre;
import fr.mybodysocial.mybodyfunding.enums.SituationFamilliale;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@NoArgsConstructor(force = true)
@Data
@RequiredArgsConstructor
@ToString
@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String nom;
    @NonNull
    private String prenom;
    @NonNull
    private String username;
    @NonNull
    @Column(nullable = false,unique = true)
    private String telephone;
    @NonNull
    private String avatar;
    //nationnalité
    @NonNull
    private String nationality;
    @JsonIgnore
    private Date date_creation;
    @NonNull
    private Date date_naissance;
    @NonNull
    @Enumerated(EnumType.STRING)
    private SituationFamilliale situation_familliale;
    @Column(nullable = true)
    private String telephoneProche;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @NonNull
    @Column(nullable = false,unique = true)
    private String email;
    @NonNull
    @JsonIgnore
    private String password;
    @NonNull
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    private Boolean locked=false;
    private Boolean enabled=false;
    private String resetPasswordToken;
    private String deleteAccountToken;
    @OneToMany(mappedBy="createur",fetch = FetchType.LAZY)
    private List<Project> projects;

    @OneToMany(mappedBy = "contributors", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Investment> investmentProject;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name="liked",
            joinColumns = @JoinColumn(name= "appuser_id"),
            inverseJoinColumns = @JoinColumn(name="project_id")
    )
    private List<Project> projectsLiked;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentProject> commentsProject;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority autority=new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(autority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
