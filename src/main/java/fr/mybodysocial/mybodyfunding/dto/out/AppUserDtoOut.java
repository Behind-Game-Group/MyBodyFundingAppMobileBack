package fr.mybodysocial.mybodyfunding.dto.out;

import fr.mybodysocial.mybodyfunding.enums.AppUserRole;
import fr.mybodysocial.mybodyfunding.enums.Genre;
import fr.mybodysocial.mybodyfunding.enums.SituationFamilliale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor(force = true)
public class AppUserDtoOut extends AbstractDtoOut {


    private String nom;
    private String prenom;
    private String username;
    private String email;
    private String telephone;
    private String avatar;
    private String nat;
    private Date date_creation;
    private Date date_naissance;
    private SituationFamilliale situationFamilliale;
    private String telephone_proche;
    private Genre genre;
    private Boolean locked;
    private Boolean enabled;
    private AppUserRole role;
}
