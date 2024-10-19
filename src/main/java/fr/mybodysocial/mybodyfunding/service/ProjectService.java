package fr.mybodysocial.mybodyfunding.service;

import fr.mybodysocial.mybodyfunding.dao.IAppUserDao;
import fr.mybodysocial.mybodyfunding.dao.ICommentProjectDao;
import fr.mybodysocial.mybodyfunding.dao.IProjectDao;
import fr.mybodysocial.mybodyfunding.dao.IProjectReportedDao;
import fr.mybodysocial.mybodyfunding.dto.handler.*;
import fr.mybodysocial.mybodyfunding.dto.in.CommentProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.ProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.ProjectReportedDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.*;
import fr.mybodysocial.mybodyfunding.enums.ProjectStatus;
import fr.mybodysocial.mybodyfunding.model.*;
import fr.mybodysocial.mybodyfunding.service.exception.EntityNotFoundException;
import fr.mybodysocial.mybodyfunding.service.exception.InconsistentRoleException;
import fr.mybodysocial.mybodyfunding.service.exception.ProjectNotFoundException;
import fr.mybodysocial.mybodyfunding.service.interfaces.IProjectService;
import fr.mybodysocial.mybodyfunding.util.Constante;
import fr.mybodysocial.mybodyfunding.util.Dates;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.mybodysocial.mybodyfunding.util.Dates.stringToDate;

@Service
@AllArgsConstructor
public class ProjectService implements IProjectService {

    private final IProjectDao iProjectDao;
    private final ICommentProjectDao iCommentDao;
    private final IProjectReportedDao iProjectReportedDao;
    private final IAppUserDao iAppUserDao;


    public Boolean isCreator(Long idUser, Long projectId) throws InconsistentRoleException{
        Project project = iProjectDao.findById(projectId).orElseThrow(() -> new InconsistentRoleException());
        AppUser appUser = iAppUserDao.findById(idUser).orElseThrow(() -> new InconsistentRoleException());
        return project.getCreateur().equals(appUser);
    }


    @Override
    public ProjectDtoOut addProject(ProjectDtoIn projectDtoIn) throws Exception {
        AppUser appUser = iAppUserDao.findById(projectDtoIn.getIdCreator()).orElseThrow(() -> new EntityNotFoundException());
        Project newProject = ProjectDtoHandler.toEntity(projectDtoIn);
        newProject.setCreateur(appUser);
        newProject.setDays_lefts(Dates.dayLeft(newProject.getStart_date(), newProject.getEnd_date()));
        newProject.setStatus(ProjectStatus.LIVE);
        appUser.getProjects().add(newProject);

        Project save = iProjectDao.save(newProject);
        return ProjectDtoHandler.toDtoOut(save);
    }

    @Override
    public List<ProjectDtoOut> getAllProjectStartsWithPattern(String pattern) throws Exception {
        List<Project> allStartWithPattern = this.iProjectDao.findByTitleStartsWith(pattern);
        return ProjectDtoHandler.entitestoDto(allStartWithPattern);
    }

    @Override
    public List<ProjectDtoOut> getAllProject() throws Exception {
        List<Project> all = iProjectDao.findAll();
        return ProjectDtoHandler.entitestoDto(all);
    }

    @Override
    public ProjectDtoOut getProjectById(Long id) throws Exception {
        Project project = iProjectDao.findById(id).orElseThrow(() -> new ProjectNotFoundException());
        return ProjectDtoHandler.toDtoOut(project);
    }

    @Override
    public ProjectDtoOut updateProject(Long id, ProjectDtoIn dtoIn) throws Exception {
        Project project = iProjectDao.findById(id).orElseThrow(() -> new ProjectNotFoundException());
        project.setTitle(dtoIn.getTitle());
        project.setIntro(dtoIn.getIntro());
        project.setShort_url(dtoIn.getShort_url());
        project.setJson_url(dtoIn.getJson_url());
        project.setUrl(dtoIn.getUrl());
        project.setStart_date(stringToDate(dtoIn.getStart_date()));
        project.setEnd_date(stringToDate(dtoIn.getEnd_date()));
        project.setImage_large(dtoIn.getImage_large());
        project.setImage_thumb(dtoIn.getImage_thumb());
        project.setVideo(dtoIn.getVideo());
        project.setCurrency(dtoIn.getCurrency());
        project.setTarget(dtoIn.getTarget());
        project.setFunding_type(dtoIn.getFunding_type());
        iProjectDao.save(project);
        return ProjectDtoHandler.toDtoOut(project);
    }

    @Override
    public ProjectDtoOut deleteProject(Long id) throws Exception {
        Project project = iProjectDao.findById(id).orElseThrow(() -> new ProjectNotFoundException());
        iProjectDao.delete(project);
        return ProjectDtoHandler.toDtoOut(project);
    }

    @Override
    public CategorieDtoOut getCategorie(Long id_project) throws Exception {
        Project entity = this.iProjectDao.findById(id_project).orElseThrow(EntityNotFoundException::new);
        return CategorieDtoHandler.dtoOutFromEntity(entity.getCategorie());
    }

    @Override
    public List<ProjectDtoOut> GetRecommendation() throws Exception {
        return ProjectDtoHandler.entitestoDto(this.iProjectDao.getProjectRecommendation());
    }

    /**
     * Like system
     */

    @Override
    public void likeProject(Long projectId, Long userId) throws Exception {
        Project project = iProjectDao.findById(projectId).orElseThrow(() -> new ProjectNotFoundException());
        AppUser appUser = iAppUserDao.findById(userId).orElseThrow(() -> new EntityNotFoundException());
        project.like(appUser);
        iProjectDao.save(project);
    }

    @Override
    public void unlikeProject(Long projectId, Long userId) throws Exception {
        Project project = iProjectDao.findById(projectId).orElseThrow(() -> new ProjectNotFoundException());
        AppUser appUser = iAppUserDao.findById(userId).orElseThrow(() -> new EntityNotFoundException());
        project.unlike(appUser);
        iProjectDao.save(project);
    }

    @Override
    public List<AppUserDtoOut> getAllUserWhoLikes(Long projectId) throws Exception {
        Project project = iProjectDao.findById(projectId).orElseThrow(() -> new ProjectNotFoundException());
        return UserDtoHandler.dtosOutfromEntities(project.getLikedList());
    }

    /*************************************************************************************************************************/

    /**
     * Comment system
     */
    @Override
    public List<CommentProjectDtoOut> getAllCommentByProjectId(Long projectId) throws Exception {
        Project target = this.iProjectDao.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        List<CommentProject> listOfComments = this.iCommentDao.getCommentProjectByProject(target);
        return CommentProjectDtoHandler.entitestoDto(listOfComments);
    }

    @Override
    public CommentProjectDtoOut postComment(Long projectId, CommentProjectDtoIn content) throws Exception {
        Project target = this.iProjectDao.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        AppUser appUser = iAppUserDao.findById(content.getIdAuthor()).orElseThrow(EntityNotFoundException::new);

        for (Investment inv : target.getContributors()) {
            if (inv.getContributors().equals(appUser)) {
                CommentProject out = target.postComment(content, appUser);
                iCommentDao.save(out);
                return CommentProjectDtoHandler.toDtoOut(out);
            }
        }
        throw new InconsistentRoleException("Not a contributor");
    }
/***********************************************************************************************************************/

    /**
     * share system
     *
     */
    @Override
    public String getSharedProjectUrl(Long projectId) throws Exception {
        String url = Constante.BASE_URL + "/project/" + projectId;
        return url;
    }

/***********************************************************************************************************************/

    /**
     * report system
     *
     */
    @Override
    public void reportProject(ProjectReportedDtoIn in, Long idproject) throws Exception {
        Project target = this.iProjectDao.findById(idproject).orElseThrow(ProjectNotFoundException::new);
        AppUser appUser = this.iAppUserDao.findById(in.getUserId()).orElseThrow(EntityNotFoundException::new);
        /* Transformer le project dto in en entity */
        /* Sauvegarder dans la bdd le signalement */
        ProjectReported out = ProjectReportedDtoHandler.toEntity(in, target, appUser);
        iProjectReportedDao.save(out);
    }

    @Override
    public List<ProjectReportedDtoOut> getAllReportProject() throws Exception {
        List<ProjectReported> all = iProjectReportedDao.findAll();
        return ProjectReportedDtoHandler.entitiestoDto(all);
    }
}
