package fr.mybodysocial.mybodyfunding.dto.handler;

import fr.mybodysocial.mybodyfunding.dto.in.ProjectReportedDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.ProjectDtoOut;
import fr.mybodysocial.mybodyfunding.dto.out.ProjectReportedDtoOut;
import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.model.Project;
import fr.mybodysocial.mybodyfunding.model.ProjectReported;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public interface ProjectReportedDtoHandler {

    static ProjectReported toEntity(ProjectReportedDtoIn dtoIn, Project projectReported,  AppUser userWhoReported) throws Exception {
        ProjectReported entity = new ProjectReported();
        entity.setUser(userWhoReported);
        entity.setProject(projectReported);
        entity.setCreationDate(new Date());
        entity.setProcessed(false);
        entity.setMessage(dtoIn.getMessage());
        return entity;
    }


    static ProjectReportedDtoOut toDtoOut(ProjectReported entity) throws Exception {
        ProjectReportedDtoOut dtoOut = new ProjectReportedDtoOut();
        dtoOut.setId(entity.getId());
        dtoOut.setUser(UserDtoHandler.dtoOutFromEntity(entity.getUser()));
        dtoOut.setProject(ProjectDtoHandler.toDtoOut(entity.getProject()));
        dtoOut.setCreationDate(entity.getCreationDate());
        dtoOut.setProcessed(entity.isProcessed());
        dtoOut.setMessage(entity.getMessage());
        return dtoOut;
    }

    static List<ProjectReportedDtoOut> entitiestoDto(List<ProjectReported> listOfProjectReported) {

        return listOfProjectReported.stream().map(e -> {
            try {
                return ProjectReportedDtoHandler.toDtoOut(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toList());
    }
}
