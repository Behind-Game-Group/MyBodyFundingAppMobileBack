package fr.mybodysocial.mybodyfunding.service;

import fr.mybodysocial.mybodyfunding.dao.IActualityProjectDao;
import fr.mybodysocial.mybodyfunding.dao.IAppUserDao;
import fr.mybodysocial.mybodyfunding.dao.IProjectDao;
import fr.mybodysocial.mybodyfunding.dto.handler.ActualityProjectDtoHandler;
import fr.mybodysocial.mybodyfunding.dto.in.ActualityProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.CommentActualityProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.ActualityProjectDtoOut;
import fr.mybodysocial.mybodyfunding.model.ActualityProject;
import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.model.CommentActualityProject;
import fr.mybodysocial.mybodyfunding.model.Project;
import fr.mybodysocial.mybodyfunding.service.exception.EntityNotFoundException;
import fr.mybodysocial.mybodyfunding.service.exception.InconsistentRoleException;
import fr.mybodysocial.mybodyfunding.service.exception.ProjectNotFoundException;
import fr.mybodysocial.mybodyfunding.service.interfaces.IActualityProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
public class ActualityProjectService implements IActualityProjectService {

    private final IProjectDao iProjectDao;
    private final IActualityProjectDao iActualityProjectDao;
    private final IAppUserDao appUserDao;

    @Override
    public List<ActualityProjectDtoOut> getActualitiesFromProject(Long idProject) throws Exception {
        Project project = iProjectDao.findById(idProject).orElseThrow(ProjectNotFoundException::new);
        List<ActualityProjectDtoOut> out = ActualityProjectDtoHandler.dtosOutfromEntities(project.getActualitiesProject());
        return out;
    }

    @Override
    public ActualityProjectDtoOut postActuality(ActualityProjectDtoIn in, Long idProject) throws Exception {
        Project project = iProjectDao.findById(idProject).orElseThrow(ProjectNotFoundException::new);
        ActualityProject newActuality = ActualityProjectDtoHandler.toEntity(in);
        newActuality.setCreationDate(new Date());
        newActuality.setProject(project);
        ActualityProjectDtoOut out = ActualityProjectDtoHandler.dtoOutFromEntity(this.iActualityProjectDao.save(newActuality));
        return out;
    }

    @Override
    public ActualityProjectDtoOut postCommentActuality(CommentActualityProjectDtoIn in) throws Exception {
        ActualityProject actuality = iActualityProjectDao.findById(in.getIdActuality()).orElseThrow(ProjectNotFoundException::new);
        AppUser user = appUserDao.findById(in.getIdUserWhoPosted()).orElseThrow(EntityNotFoundException::new);
        CommentActualityProject newComment = new CommentActualityProject();
        newComment.setActualities(actuality);
        newComment.setPostedBy(user);
        newComment.setContent(in.getContent());
        newComment.setPostDate(new Date());
        actuality.getComments().add(newComment);
        return ActualityProjectDtoHandler.dtoOutFromEntity(this.iActualityProjectDao.save(actuality));
    }
}