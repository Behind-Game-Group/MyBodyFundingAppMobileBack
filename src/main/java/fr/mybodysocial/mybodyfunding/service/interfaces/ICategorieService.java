package fr.mybodysocial.mybodyfunding.service.interfaces;

import fr.mybodysocial.mybodyfunding.dto.in.CategorieDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.CategorieDtoOut;

import java.util.List;

public interface ICategorieService {
    //avoir le nombre d'investisseurs par categorie
    Integer getNombreBackers(String categorie) throws Exception;

    // le taux de succes par categorie
    float getPercent(String categorie) throws Exception;

    //avoir les projets d'une categorie donnée

    List<CategorieDtoOut> getAllCategories() throws Exception;

    CategorieDtoOut addCategorie(CategorieDtoIn newCategorie) throws Exception;

    CategorieDtoOut deleteCategorie(Long id) throws Exception;

    CategorieDtoOut updateCategorie(Long id, CategorieDtoIn categorieUpdated) throws Exception;
}
