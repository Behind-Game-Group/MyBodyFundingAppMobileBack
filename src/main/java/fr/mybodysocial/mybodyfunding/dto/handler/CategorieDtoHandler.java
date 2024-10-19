package fr.mybodysocial.mybodyfunding.dto.handler;

import fr.mybodysocial.mybodyfunding.dto.in.CategorieDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.CategorieDtoOut;
import fr.mybodysocial.mybodyfunding.model.Categorie;

import java.util.ArrayList;
import java.util.List;

public interface CategorieDtoHandler {

    /**
     * transformer un CategorieDto en Categorie
     *
     * @param pDto DTO In
     * @return entity
     */
    static Categorie toEntity(CategorieDtoIn pDto) {
        var result = new Categorie();
        result.setTitle(pDto.getTitle());
        result.setImage_url(pDto.getImageUrl());
        return result;
    }

    /**
     * transform entity to dtoout
     *
     * @param pEntity entité
     * @return dto Out
     */
    static CategorieDtoOut dtoOutFromEntity(Categorie pEntity) {
        var result = new CategorieDtoOut();
        result.setId(pEntity.getId());
        result.setTitle(pEntity.getTitle());
        result.setImageUrl(pEntity.getImage_url());
        return result;
    }

    /**
     * transforme some entities to dto out
     *
     * @param Categorie liste of entities
     * @return liste of dto Out
     */
    static List<CategorieDtoOut> dtosOutfromEntities(List<Categorie> categories) {
        List<CategorieDtoOut> categorieDtoOuts = new ArrayList<>();

        if (categories != null && !categories.isEmpty()) {
            categories.forEach(elm -> categorieDtoOuts.add(CategorieDtoHandler.dtoOutFromEntity(elm)));
        }
        return categorieDtoOuts;
    }

    /**
     * transform entity to dto In
     *
     * @param Categorie entity
     * @return dto In
     */
    static CategorieDtoIn dtoInfromEntity(Categorie cat) {
        var result = new CategorieDtoIn();
        result.setTitle(cat.getTitle());
        result.setImageUrl(cat.getImage_url());
        return result;
    }
}
