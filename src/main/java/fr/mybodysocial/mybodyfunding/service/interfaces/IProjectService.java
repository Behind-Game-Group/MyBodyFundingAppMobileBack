package fr.mybodysocial.mybodyfunding.service.interfaces;


import fr.mybodysocial.mybodyfunding.dto.in.CommentProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.ProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.ProjectReportedDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.*;

import java.util.List;

public interface IProjectService {

    //avoir la categorie du projet
    CategorieDtoOut getCategorie(Long id_project) throws Exception;

    ProjectDtoOut addProject(ProjectDtoIn projectDtoIn) throws Exception;

    List<ProjectDtoOut> getAllProject() throws Exception;

    List<ProjectDtoOut> getAllProjectStartsWithPattern(String pattern) throws Exception;

    ProjectDtoOut getProjectById(Long id) throws Exception;

    ProjectDtoOut updateProject(Long id, ProjectDtoIn dtoIn) throws Exception;

    ProjectDtoOut deleteProject(Long id) throws Exception;

    void likeProject(Long projectId, Long userId) throws Exception;

    void unlikeProject(Long projectId, Long userId) throws Exception;

    List<AppUserDtoOut> getAllUserWhoLikes(Long projectId) throws Exception;

    List<CommentProjectDtoOut> getAllCommentByProjectId(Long projectId) throws Exception;

    CommentProjectDtoOut postComment(Long projectId, CommentProjectDtoIn content) throws Exception;

    String getSharedProjectUrl(Long projectId) throws Exception;

    void reportProject(ProjectReportedDtoIn in, Long idProject) throws Exception;

    List<ProjectReportedDtoOut> getAllReportProject() throws Exception;

    List<ProjectDtoOut> GetRecommendation() throws Exception;
}



