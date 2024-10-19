package fr.mybodysocial.mybodyfunding.service.interfaces;

import fr.mybodysocial.mybodyfunding.dto.in.ActualityProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.CommentActualityProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.ActualityProjectDtoOut;
import fr.mybodysocial.mybodyfunding.model.CommentActualityProject;

import java.util.List;

public interface IActualityProjectService {
    public List<ActualityProjectDtoOut> getActualitiesFromProject(Long idProject) throws Exception;
    ActualityProjectDtoOut postActuality(ActualityProjectDtoIn in, Long projetId) throws Exception;

    ActualityProjectDtoOut postCommentActuality(CommentActualityProjectDtoIn in) throws Exception;
}