package fr.mybodysocial.mybodyfunding.dto.handler;

import fr.mybodysocial.mybodyfunding.dto.in.CommentProjectDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.CommentProjectDtoOut;
import fr.mybodysocial.mybodyfunding.model.CommentProject;

import java.util.List;
import java.util.stream.Collectors;

public interface CommentProjectDtoHandler {
    /**
     * transformer un dto to entite
     *
     * @param pdto : dtoIn
     * @return Project: entite
     * @throws Exception
     */
    static CommentProject toEntity(CommentProjectDtoIn cpdtoi) throws Exception {
        var result = new CommentProject();
        result.setContent(cpdtoi.getContent());
        return result;
    }

    /**
     * entity to dto
     *
     * @param CommentProject
     * @return dto
     * @throws Exception
     */
    static CommentProjectDtoOut toDtoOut(CommentProject comment) throws Exception {
        var result = new CommentProjectDtoOut();
        result.setIdAuthor(comment.getAuthor().getId());
        result.setContent(comment.getContent());
        result.setIdParent(comment.getIdParent());
        result.setDateOfPosting(comment.getDateComment());
        return result;
    }

    static List<CommentProjectDtoOut> entitestoDto(List<CommentProject> comments) {

        return comments.stream().map(e -> {
            try {
                return CommentProjectDtoHandler.toDtoOut(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toList());
    }
}
