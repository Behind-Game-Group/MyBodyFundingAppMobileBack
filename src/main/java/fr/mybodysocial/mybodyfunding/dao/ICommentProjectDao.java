package fr.mybodysocial.mybodyfunding.dao;

import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.model.CommentProject;
import fr.mybodysocial.mybodyfunding.model.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentProjectDao extends IAbstractDao<CommentProject> {
    List<CommentProject> getCommentProjectByProject(Project target);

    List<CommentProject> getCommentProjectByAuthor(AppUser target);
}
