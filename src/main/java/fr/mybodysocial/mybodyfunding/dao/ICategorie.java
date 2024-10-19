package fr.mybodysocial.mybodyfunding.dao;

import fr.mybodysocial.mybodyfunding.model.Categorie;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ICategorie extends IAbstractDao<Categorie> {

    Optional<Categorie> findByTitle(String categorie);
}
