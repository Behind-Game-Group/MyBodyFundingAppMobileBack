package fr.mybodysocial.mybodyfunding.dto.in;

import fr.mybodysocial.mybodyfunding.enums.AppUserRole;
import fr.mybodysocial.mybodyfunding.enums.Genre;
import fr.mybodysocial.mybodyfunding.enums.SituationFamilliale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
public class AppUserDtoIn {

    private String nom;
    private String prenom;
    private String username;
    private String email;
    private String password;
    private String telephone;
    private String avatar;
    private String nat;
    private String date_naissance;
    private SituationFamilliale situationFamilliale;
    private String telephone_proche;
    private Genre genre;
    private AppUserRole role;

}
