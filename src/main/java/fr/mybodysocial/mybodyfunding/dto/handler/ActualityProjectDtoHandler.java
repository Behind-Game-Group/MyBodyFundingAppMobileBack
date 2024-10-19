package fr.mybodysocial.mybodyfunding.dto.handler;

import fr.mybodysocial.mybodyfunding.dto.in.ActualityProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.ActualityProjectDtoOut;
import fr.mybodysocial.mybodyfunding.model.ActualityProject;

import java.util.ArrayList;
import java.util.List;

public interface ActualityProjectDtoHandler {

    /**
     * transformer un CategorieDto en Categorie
     * @param pDto DTO In
     * @return entity
     */
    public static ActualityProject toEntity(ActualityProjectDtoIn pDto) throws Exception{
        var result=new ActualityProject();;
        result.setContent(pDto.getContent());
        result.setTitle(pDto.getTitle());
        result.setPhotosUrl(pDto.getPhotosUrl());
        result.setVideosUrl(pDto.getVideosUrl());
        return result;
    }

    /**
     * transform entity to dtoout
     * @param pEntity entité
     * @return dto Out
     */
    public static ActualityProjectDtoOut dtoOutFromEntity(ActualityProject pEntity) throws Exception{
        var result =new ActualityProjectDtoOut();

        result.setProject(ProjectDtoHandler.toDtoOut(pEntity.getProject()));
        result.setContent(pEntity.getContent());
        result.setComments(pEntity.getComments());
        result.setTitle(pEntity.getTitle());
        result.setPhotosUrl(pEntity.getPhotosUrl());
        result.setCreationDate(pEntity.getCreationDate());
        result.setVideosUrl(pEntity.getVideosUrl());
        return result;
    }

    public static List<ActualityProjectDtoOut> dtosOutfromEntities(List<ActualityProject> entities) throws Exception{
        List<ActualityProjectDtoOut> out =new ArrayList<>();

        if(entities!=null && !entities.isEmpty()){
            entities.forEach(elm-> {
                try {
                    out.add(ActualityProjectDtoHandler.dtoOutFromEntity(elm));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return out;
    }
}