package fr.mybodysocial.mybodyfunding.dao;

import fr.mybodysocial.mybodyfunding.model.ActualityProject;
import fr.mybodysocial.mybodyfunding.model.Project;
import java.util.List;

public interface IActualityProjectDao extends IAbstractDao<ActualityProject> {
    List<ActualityProject> getActualityProjectByProject(Project project);

}