package fr.mybodysocial.mybodyfunding.service;

import fr.mybodysocial.mybodyfunding.dao.ICategorie;
import fr.mybodysocial.mybodyfunding.dao.IProjectDao;
import fr.mybodysocial.mybodyfunding.dto.handler.CategorieDtoHandler;
import fr.mybodysocial.mybodyfunding.dto.in.CategorieDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.CategorieDtoOut;
import fr.mybodysocial.mybodyfunding.model.Categorie;
import fr.mybodysocial.mybodyfunding.model.Project;
import fr.mybodysocial.mybodyfunding.service.exception.EntityNotFoundException;
import fr.mybodysocial.mybodyfunding.service.interfaces.ICategorieService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategorieService implements ICategorieService {

    private final ICategorie categoriedao;
    private final IProjectDao projectDao;
    public final static String CATEGORIE_NOT_FOUND_MSG = "user with email %s not found";

    @Override
    public Integer getNombreBackers(String categorieTitle) throws Exception {

        Categorie categorie = categoriedao.findByTitle(categorieTitle).orElseThrow(() -> new UsernameNotFoundException(String.format(CATEGORIE_NOT_FOUND_MSG, categorieTitle)));

        List<Project> projects = projectDao.findByCategorie(categorie.getId());
        if (!projects.isEmpty()) {

            return projects.stream().map(e -> e.getNumberOfBackers()).reduce(0, Integer::sum);

        } else {
            throw new Exception("la liste des projets de la categorie " + categorie.getTitle() + " est vide");
        }

    }

    @Override
    public float getPercent(String categorie) throws Exception {
        return 0;
    }

    @Override
    public List<CategorieDtoOut> getAllCategories() throws Exception {
        List<Categorie> categories = this.categoriedao.findAll();
        return CategorieDtoHandler.dtosOutfromEntities(categories);
    }


    @PreAuthorize("Role")
    @Override
    public CategorieDtoOut addCategorie(CategorieDtoIn newCategorie) {
        Categorie save = this.categoriedao.save(CategorieDtoHandler.toEntity(newCategorie));
        return CategorieDtoHandler.dtoOutFromEntity(save);
    }

    @Override
    public CategorieDtoOut deleteCategorie(Long id) throws Exception {
        Categorie target = this.categoriedao.findById(id).orElseThrow(EntityNotFoundException::new);
        this.categoriedao.delete(target);
        return CategorieDtoHandler.dtoOutFromEntity(target);
    }

    @Override
    public CategorieDtoOut updateCategorie(Long id, CategorieDtoIn categorieUpdated) throws Exception {
        Categorie target = this.categoriedao.findById(id).orElseThrow(EntityNotFoundException::new);
        Categorie updated = CategorieDtoHandler.toEntity(categorieUpdated);

        target.setTitle(updated.getTitle());
        target.setImage_url(updated.getImage_url());

        return CategorieDtoHandler.dtoOutFromEntity(target);
    }
}
