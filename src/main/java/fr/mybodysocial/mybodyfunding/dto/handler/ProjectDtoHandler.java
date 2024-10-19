package fr.mybodysocial.mybodyfunding.dto.handler;

import fr.mybodysocial.mybodyfunding.dto.in.ProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.ProjectDtoOut;
import fr.mybodysocial.mybodyfunding.model.Project;
import fr.mybodysocial.mybodyfunding.util.Dates;

import java.util.List;
import java.util.stream.Collectors;

public interface ProjectDtoHandler {

    /**
     * transformer un dto to entite
     *
     * @param pdto : dtoIn
     * @return Project: entite
     * @throws Exception
     */
    static Project toEntity(ProjectDtoIn pdto) throws Exception {
        var result = new Project();
        result.setTitle(pdto.getTitle());
        result.setImage_large(pdto.getImage_large());
        result.setImage_thumb(pdto.getImage_thumb());
        result.setFunding_type(pdto.getFunding_type());
        result.setIntro(pdto.getIntro());
        result.setJson_url(pdto.getJson_url());
        result.setUrl(pdto.getUrl());
        result.setShort_url(pdto.getShort_url());
        result.setVideo(pdto.getVideo());
        result.setStart_date(Dates.stringToDate(pdto.getStart_date()));
        result.setEnd_date(Dates.stringToDate(pdto.getEnd_date()));
        //result.setCreateur(pdto.getAuthor());
        result.setCurrency(pdto.getCurrency());
        result.setTarget(pdto.getTarget());
        return result;
    }

    /**
     * entity to dto
     *
     * @param project
     * @return dto
     * @throws Exception
     */
    static ProjectDtoOut toDtoOut(Project project) throws Exception {
        var result = new ProjectDtoOut();
        result.setId(project.getId());
        result.setTitle(project.getTitle());
        result.setImage_large(project.getImage_large());
        result.setImage_thumb((project.getImage_thumb()));
        result.setFunding_type(project.getFunding_type());
        result.setIntro(project.getIntro());
        result.setJson_url(project.getJson_url());
        result.setUrl(project.getUrl());
        result.setShort_url(project.getShort_url());
        result.setVideo(project.getVideo());
        result.setStart_date(project.getStart_date());
        result.setEnd_date(project.getEnd_date());
        result.setTarget(project.getTarget());
        result.setCreateur(UserDtoHandler.dtoOutFromEntity(project.getCreateur()));
        result.setCurrency(project.getCurrency());
        result.setPledged(project.getPledged());
        result.setUpdates(project.getUpdates());
        result.setStatus(project.getStatus());
        result.setNumberOfBackers(project.getNumberOfBackers());
        return result;
    }

    static List<ProjectDtoOut> entitestoDto(List<Project> projects) {

        return projects.stream().map(e -> {
            try {
                return ProjectDtoHandler.toDtoOut(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toList());
    }


}


