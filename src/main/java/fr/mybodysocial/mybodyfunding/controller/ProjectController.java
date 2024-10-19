package fr.mybodysocial.mybodyfunding.controller;

import fr.mybodysocial.mybodyfunding.dto.in.*;
import fr.mybodysocial.mybodyfunding.dto.out.*;
import fr.mybodysocial.mybodyfunding.service.interfaces.IActualityProjectService;
import fr.mybodysocial.mybodyfunding.service.interfaces.IInvestmentService;
import fr.mybodysocial.mybodyfunding.service.interfaces.IProjectService;
import fr.mybodysocial.mybodyfunding.util.Constante;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:19006")
@SecurityRequirement(name= "Bearer Authentification")
@RequestMapping(Constante.BASE_URL + "/project")
public class ProjectController {

    private static final Logger LOG = LogManager.getLogger();

    private final IProjectService projectService;

    private final IInvestmentService investmentService;

    private final IActualityProjectService aps;

    @Operation(tags = {"Project API"}, summary = "get all projects", description = "method allow to get all projects from the db")
    @GetMapping("/")
    ResponseEntity<List<ProjectDtoOut>> findAll() throws Exception {
        return ResponseEntity.ok(projectService.getAllProject());
    }

    @Operation(tags = {"Project API"}, summary = "get all projects", description = "method allow to get all projects from the db")
    @GetMapping("/projectRecommendation")
    ResponseEntity<List<ProjectDtoOut>> projectRecommendation() throws Exception {
        return ResponseEntity.ok(this.projectService.GetRecommendation());
    }
    @Operation(tags = {"Project API"}, summary = "add a project", description = "method allow to add a project to the db and return it")
    @PreAuthorize("hasAnyAuthority('CREATEUR', 'ADMIN')")
    @RequestMapping(value = "/", method = {RequestMethod.POST})
    ResponseEntity<ProjectDtoOut> addProject(@RequestBody ProjectDtoIn dtoIn) throws Exception {
        return ResponseEntity.ok(projectService.addProject(dtoIn));
    }

    @Operation(tags = {"Project API"}, summary = "Delete a project", description = "method allow to delete a project from the db and return it (Only Admin)")
    @PreAuthorize("hasAnyAuthority('ADMIN') or @projectService.isCreator(authentication.principal.id, #id) ")
    @DeleteMapping("/{id}")
    ResponseEntity<ProjectDtoOut> deleteProject(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(projectService.deleteProject(id));
    }

    @Operation(tags = {"Project API"}, summary = "get a project by id", description = "method allow to get a project by his id from the db")
    @GetMapping("/{id}")
    ResponseEntity<ProjectDtoOut> getProject(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @Operation(tags = {"Project API"}, summary = "update a project", description = "method allow to update a project to the db and return it (Only Admin)")
    @PreAuthorize("hasAnyAuthority('ADMIN') or @projectService.isCreator(authentication.principal.id, #id) ")
    @PutMapping("/{id}")
    ResponseEntity<ProjectDtoOut> updateProject(@PathVariable(name = "id") Long id, @RequestBody ProjectDtoIn projectUpdated) throws Exception {
        return ResponseEntity.ok(projectService.updateProject(id, projectUpdated));
    }

    @Operation(tags = {"Project API"}, summary = "get a project by his name", description = "method allow to get a project from the db by his name, use it for predictive text")
    @GetMapping("/searchByName")
    ResponseEntity<List<ProjectDtoOut>> getAllProjectStartingWithPattern(@RequestParam(name = "pattern") String pattern) throws Exception {
        return ResponseEntity.ok(this.projectService.getAllProjectStartsWithPattern(pattern));
    }

    @Operation(tags = {"Project API"}, summary = "like a project", description = "method allow to like a project and save it in the bd")
    @PostMapping("/{projectId}/like")
    public ResponseEntity<Void> likeProject(@PathVariable Long projectId, @RequestBody Long userId) throws Exception {
        projectService.likeProject(projectId, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(tags = {"Project API"}, summary = "unlike a project", description = "method allow to unlike a project and save it in the bd")
    @PostMapping("/{projectId}/unlike")
    public ResponseEntity<Void> unlikeProject(@PathVariable Long projectId, @RequestBody Long userId) throws Exception {
        projectService.unlikeProject(projectId, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(tags = {"Project API"}, summary = "get all comments by project id", description = "method allow to get all comments from a project by his id")
    @GetMapping("/{projectId}/comments")
    public ResponseEntity<List<CommentProjectDtoOut>> getCommentsByProjectId(@PathVariable Long projectId) throws Exception {
        return ResponseEntity.ok(this.projectService.getAllCommentByProjectId(projectId));
    }

    @Operation(tags = {"Project API"}, summary = "add comment", description = "method allow to add a comment in a project and save it in bd")
    @PostMapping("/{projectId}/comments")
    public ResponseEntity<CommentProjectDtoOut> postComment(@PathVariable Long projectId, @RequestBody CommentProjectDtoIn content) throws Exception {
        return ResponseEntity.ok(this.projectService.postComment(projectId, content));
    }

    @Operation(tags = {"Project API"}, summary = "get all contributors by project id", description = "method allow to get all contributors from a project by his id")
    @PreAuthorize("hasAnyAuthority('ADMIN') or @projectService.isCreator(authentication.principal.id, #id) ")
    @GetMapping("/{id}/contributors")
    ResponseEntity<List<AppUserDtoOut>> getContributors(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(this.investmentService.getContributors(id));
    }

    @Operation(tags = {"Project API"}, summary = "get all investments by project id", description = "method allow to get all investments from a project by his id")
    @PreAuthorize("hasAnyAuthority('ADMIN') or @projectService.isCreator(authentication.principal.id, #id) ")
    @GetMapping("/{id}/investments")
    ResponseEntity<List<InvestmentDtoOut>> getAllInvestmentForAProject(@PathVariable Long id) throws Exception {
        System.out.println("-------------" + SecurityContextHolder.getContext().getAuthentication().getName() + " -----------------------------");
        return ResponseEntity.ok(this.investmentService.getAllInvestmentForAProject(id));
    }

    @Operation(tags = {"Project API"}, summary = "get all user who likes by project id", description = "method allow to get all the user who likes a project by his id")
    @GetMapping("/{id}/liked")
    ResponseEntity<List<AppUserDtoOut>> getAllPeopleWhoLikesByIdProject(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(this.projectService.getAllUserWhoLikes(id));
    }

    @Operation(tags = {"Project API"}, summary="report a project", description = "method allow to report a project to an admin")
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{id}/report")
    public ResponseEntity<Void> reportProject(@PathVariable Long id, @RequestBody ProjectReportedDtoIn in) throws Exception {
        projectService.reportProject(in, id);
        return ResponseEntity.status(201).build();
    }

    @Operation(tags = {"Project API"}, summary = "get all projects Reported", description = "method allow to get all projects reported from the db")
//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/reported")
    public ResponseEntity<List<ProjectReportedDtoOut>> findAllReportProject() throws Exception {
        return ResponseEntity.ok(projectService.getAllReportProject());
    }

    @Operation(tags = {"Project API"}, summary="get project URL", description = "method allow to get the URL of a project and share it")
    @PreAuthorize("hasAnyAuthority('CREATEUR', 'ADMIN', 'USER')")
    @GetMapping("/{id}/url")
    public ResponseEntity<String> getProjectUrl(@PathVariable Long id) throws Exception {
        String url = projectService.getSharedProjectUrl(id);
        return ResponseEntity.ok(url);
    }

    /********************************************************************************************************************************/

    /**
     *
     * Actuality system
     * */
     @Operation(tags = {"Project API"}, summary = "Get actualities from a project", description = "method allow to get all actualities from a project id")
    @GetMapping("/{id}/actualities")
    ResponseEntity<List<ActualityProjectDtoOut>> getAllActualitiesFromProject(@PathVariable(name="id") Long id) throws Exception{
        return ResponseEntity.ok(aps.getActualitiesFromProject(id));
    }

    @PostMapping("/{id}/actualities")
    @PreAuthorize("hasAnyAuthority('ADMIN') or @projectService.isCreator(authentication.principal.id, #id) ")
    ResponseEntity<ActualityProjectDtoOut> postActuality(@PathVariable(name="id") Long id, @RequestBody ActualityProjectDtoIn in) throws Exception{
         return ResponseEntity.ok(aps.postActuality(in, id));
    }

    @PostMapping("/{id}/actualities/comments")
    ResponseEntity<ActualityProjectDtoOut> postCommentActuality(@PathVariable(name="id") Long id, @RequestBody CommentActualityProjectDtoIn in) throws Exception{
         return ResponseEntity.ok(aps.postCommentActuality(in));
    }
    /********************************************************************************************************************************/
}
