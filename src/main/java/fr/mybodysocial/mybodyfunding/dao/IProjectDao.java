package fr.mybodysocial.mybodyfunding.dao;

import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface IProjectDao extends IAbstractDao<Project> {

    List<Project> findByCategorie(Long Id) throws Exception;

    List<Project> findByTitleStartsWith(String pattern) throws Exception;

    List<AppUser> findContributeursById(Long id) throws Exception;
    @Query(value = "SELECT * FROM project ORDER BY project.likes DESC LIMIT 5;", nativeQuery = true)
    List<Project> getProjectRecommendation() throws Exception;
}

