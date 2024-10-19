package fr.mybodysocial.mybodyfunding.controller;

import fr.mybodysocial.mybodyfunding.dto.handler.UserDtoHandler;
import fr.mybodysocial.mybodyfunding.dto.in.AppUserDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.ConnectionDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.ForgetPasswordDto;
import fr.mybodysocial.mybodyfunding.dto.out.*;
import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.service.*;
import fr.mybodysocial.mybodyfunding.service.exception.EntityNotFoundException;
import fr.mybodysocial.mybodyfunding.service.exception.FonctionnelleException;
import fr.mybodysocial.mybodyfunding.service.exception.InconsistentStatusException;
import fr.mybodysocial.mybodyfunding.service.exception.SendMailException;
import fr.mybodysocial.mybodyfunding.util.Constante;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.boot.jaxb.Origin;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User Controller
 */
@RestController
@RequestMapping(Constante.BASE_URL + "/user")
@CrossOrigin(origins = "http://localhost:19006")
@AllArgsConstructor
public class UserRestController extends AbstractController {
    private static final Logger LOG = LogManager.getLogger();

    private final AppUserService appUserService;

    private final InvestmentService investmentService;
    private final AuthentificationService authService;
    private final ConfirmationTokenService confirmationTokenService;

    /**
     * Ajouter un utilisateur
     *
     * @param dtoIn
     * @return
     * @throws FonctionnelleException
     * @throws SendMailException
     */
    @Operation(tags = {
            "User API"}, summary = "Add a user.", description = "Will add a user into the data base. Will return it with its id when done.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Your user was added and returned in the response body.", content = @Content(schema = @Schema(implementation = AppUserDtoOut.class))),
            @ApiResponse(responseCode = "400", description = "Your user is not valid.", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class))),
            @ApiResponse(responseCode = "412", description = "User is already in the data base (email must be unique) or there is a role problem.", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class)))})
    @RequestMapping(value = "/register", method = {RequestMethod.PUT, RequestMethod.POST})
    ResponseEntity<AppUserDtoOut> signUp(@Parameter(description = "User object that will be stored in database", required = true) @RequestBody AppUserDtoIn dtoIn) throws Exception {

        UserRestController.LOG.info("--> registerUser - {}", dtoIn);
        var savedUser = authService.register(dtoIn);
        var result = UserDtoHandler.dtoOutFromEntity(savedUser);
        UserRestController.LOG.info("<-- registerUser - New user  {}", result);
        return ResponseEntity.status(201).body(result);
    }

    @Operation(tags = {
            "User API"}, summary = "log a user.", description = "Method to log a user")
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    ResponseEntity<LoginAppUserDtoOut> signIn(@RequestBody(required = true) ConnectionDtoIn dtoIn) throws Exception {
        var userLogged = authService.signInUser(dtoIn);
        return ResponseEntity.ok(userLogged);
    }

    @GetMapping("confirm")
    public String confirm(@RequestParam("token") String token) {
        return confirmationTokenService.confirmToken(token);
    }

    @PatchMapping("update/{userId}")
    @SecurityRequirement(name= "Bearer Authentification")
    @PreAuthorize("hasAuthority('ADMIN') or #userId==authentication.principal.id")
    @Operation(tags = {
            "User API"}, summary = "update a user.", description = "Will update a user . Will return the new one.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Your user was updated and returned in the response body.", content = @Content(schema = @Schema(implementation = AppUserDtoOut.class))),
            @ApiResponse(responseCode = "400", description = "Your user is not valid.", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class))),
            @ApiResponse(responseCode = "412", description = "User is already in the data base (email must be unique) or there is a role problem.", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class)))})
    public ResponseEntity<?> updateUser(@Parameter(description = "id User  that you want to change it", required = true) @PathVariable("userId") Long userId, @Parameter(description = "User object that will be stored in database", required = true) @RequestBody AppUserDtoIn user) throws Exception {
        UserRestController.LOG.info("--> updateUser - {} - {}", userId, user);
        AppUser update = appUserService.update(userId, user);
        AppUserDtoOut appUserDtoOut = UserDtoHandler.dtoOutFromEntity(update);
        UserRestController.LOG.info("<-- updateUser - User {} is updated by user {}", appUserDtoOut.getId(), this.getConnectedUserId());
        return ResponseEntity.ok(appUserDtoOut);
    }

    /**
     * disactivate a user
     * you need to be connected or you are the ADMIN
     * User deactivated cannot do anything until they are reactivated.
     *
     * @param pUserId
     * @return AppUser: the user desactivated
     * @throws EntityNotFoundException
     */
    @PatchMapping("/desactivate/{userId}")
    @SecurityRequirement(name= "Bearer Authentification")
    @PreAuthorize("hasAuthority('ADMIN') or #pUserId==authentication.principal.id")
    @Operation(tags = {
            "User API"}, summary = "Deactivates a user.", description = "Will deactivate a user already present in the data base. Will return it when done. Will change the user's status to DISABLED (1). You must be connected or have the ADMIN role in order to deactivate someone.", security = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Your user was deactivated and returned in the response body.", content = @Content(schema = @Schema(implementation = AppUserDtoOut.class))),
            @ApiResponse(responseCode = "400", description = "Your userId is not valid.", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class))),
            @ApiResponse(responseCode = "401", description = "You are not connected or you are not  ADMIN.", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class))),
            @ApiResponse(responseCode = "412", description = "The element to deactivate does not exist", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class)))})
    public ResponseEntity<AppUserDtoOut> deactivateUserById(@Parameter(description = "The user's id", required = true) @PathVariable("userId") Long pUserId) throws EntityNotFoundException, InconsistentStatusException {

        UserRestController.LOG.info("--> deactivateUserById - {}", pUserId);
        var result = appUserService.disable(pUserId);
        var dtoOut = UserDtoHandler.dtoOutFromEntity(result);
        UserRestController.LOG.info("<-- deactivateUserById - User {} is deactivated By {}", pUserId,
                this.getConnectedUserId());
        return ResponseEntity.ok(dtoOut);

    }

    /**
     * Activate a user
     * you need to be connected or you are the ADMIN
     *
     * @param pUserId
     * @return AppUser: the user Activated
     * @throws EntityNotFoundException
     */
    @PatchMapping("/activate/{userId}")
    @SecurityRequirement(name= "Bearer Authentification")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(tags = {
            "User API"}, summary = "Activate a user.", description = "Will activate a user already present in the data base. Will return it when done. Will change the user's status to ENABLED (1).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Your user was deactivated and returned in the response body.", content = @Content(schema = @Schema(implementation = AppUserDtoOut.class))),
            @ApiResponse(responseCode = "400", description = "Your userId is not valid.", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class))),
            @ApiResponse(responseCode = "401", description = "You are not connected or you are not  ADMIN.", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class))),
            @ApiResponse(responseCode = "412", description = "The element to deactivate does not exist", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class)))})
    public ResponseEntity<AppUserDtoOut> activateUserById(@Parameter(description = "The user's id", required = true) @PathVariable("userId") Long pUserId) throws EntityNotFoundException, InconsistentStatusException {

        UserRestController.LOG.info("--> activateUserById - {}", pUserId);
        var result = appUserService.enable(pUserId);
        var dtoOut = UserDtoHandler.dtoOutFromEntity(result);
        UserRestController.LOG.info("<-- activateUserById - User {} is activated By {}", pUserId,
                this.getConnectedUserId());
        return ResponseEntity.ok(dtoOut);

    }

    @Operation(tags = {"User API"}, summary = "find all users.", description = "return all users  already present in the data base. only Admin can do that")
    @SecurityRequirement(name= "Bearer Authentification")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("findAll")
    public ResponseEntity<List<AppUserDtoOut>> findAllUsers() {
        UserRestController.LOG.info("-->find All Users ");
        var result = appUserService.findAllUsers();
        UserRestController.LOG.info("<--find All Users {} ", result);

        return ResponseEntity.ok(UserDtoHandler.dtosOutfromEntities(result));
    }

    /*** DELETE ACCOUNT SYSTEM ***********************************************************************************/


    /**
     * Deletes a user. <br>
     * <p>
     * You need to be connected. <br>
     * only ADMIN can delete a user from the data base
     * <br>
     *
     * @param pUserId id of the user to be deleted
     * @return the user deleted
     * @throws InconsistentStatusException if an error occurred
     * @throws EntityNotFoundException     if an error occurred
     */
    @DeleteMapping("/deleteAccount/{userId}")
    @SecurityRequirement(name= "Bearer Authentification")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(tags = {
            "User API"}, summary = "Deletes a user.", description = "Will delete a user already present in the data base")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Your user was deleted", content = @Content(schema = @Schema(implementation = AppUserDtoOut.class))),
            @ApiResponse(responseCode = "400", description = "Your userId is not valid.", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class))),
            @ApiResponse(responseCode = "412", description = "The element to delete does not exist.", content = @Content(schema = @Schema(implementation = ExceptionDtoOut.class)))})
    public ResponseEntity<?> deleteUserById(
            @Parameter(description = "The user's id", required = true) @PathVariable("userId") Long pUserId)
            throws EntityNotFoundException, InconsistentStatusException {

        UserRestController.LOG.info("--> deleteUserById - {}", pUserId);
        appUserService.delete(pUserId);
        UserRestController.LOG.info("<-- deleteUserById - User {} is deleted by user {}", pUserId,
                this.getConnectedUserId());
        return ResponseEntity.ok("l'utilisateur a ete bien supprime de la base de donnees");
    }

    @GetMapping("/delete/{userId}")
    @SecurityRequirement(name= "Bearer Authentification")
    @PreAuthorize("hasAuthority('ADMIN') or #id==authentication.principal.id")
    public ResponseEntity<String> deleteAccount(@Parameter(description = "The user's id", required = true) @PathVariable("userId") Long id) throws Exception{
        this.appUserService.generateDeleteAccountToken(id);
        return ResponseEntity.ok("Email send");
    }

    @GetMapping("/user/delete")
    @SecurityRequirement(name= "Bearer Authentification")
    public ResponseEntity<?> deleteAccountWithToken(@RequestParam(name = "token") String token) throws Exception {
        this.appUserService.deleteUserWithDeleteTokenAccount(token);
        return ResponseEntity.ok("");
    }

    /***************************************************************************************************************/


    @Operation(tags = {
            "User API"}, summary = "Get all comments create by a user", description = "Get all comments create by a user by his id")
    @PreAuthorize("hasAuthority('ADMIN') or #id==authentication.principal.id")
    @GetMapping("/{id}/comments")
    @SecurityRequirement(name= "Bearer Authentification")
    public ResponseEntity<List<CommentProjectDtoOut>> getComments(@PathVariable(name = "id") long id) throws Exception {
        return ResponseEntity.ok(appUserService.getUserAllComments(id));
    }

    @GetMapping("/{id}/getInvestments")
    @Operation(tags = {
            "User API"}, summary = "Get all investments by user", description = "Get all investments by user by his id")
    @PreAuthorize("hasAuthority('ADMIN') or #id==authentication.principal.id")
    @SecurityRequirement(name= "Bearer Authentification")
    public ResponseEntity<List<InvestmentDtoOut>> getInvestmentsBydUser(@PathVariable(name = "id") long id) throws Exception {
        return ResponseEntity.ok(appUserService.getUserAllInvestments(id));
    }

    @GetMapping("/{id}/liked")
    @Operation(tags = {
            "User API"}, summary = "Get all projects liked by a user", description = "Get all projects liked by a user by his id")
    @PreAuthorize("hasAuthority('ADMIN') or #id==authentication.principal.id")
    @SecurityRequirement(name= "Bearer Authentification")
    public ResponseEntity<List<ProjectDtoOut>> getProjectLikedList(@PathVariable(name = "id") Long userId) throws Exception {
        return ResponseEntity.ok(appUserService.getAllProjectLiked(userId));
    }

    @GetMapping("/{id}/investment")
    @PreAuthorize("hasAuthority('ADMIN') or #id==authentication.principal.id")
    @SecurityRequirement(name= "Bearer Authentification")
    public ResponseEntity<Double> getSumOfInvestmentForAUser(@PathVariable(name = "id") Long userId) throws Exception {
        return ResponseEntity.ok(this.investmentService.getAmountOfInvestmentForAUser(userId));
    }

    /**
     * Forget password system
     */

    @PostMapping("forget_password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetPasswordDto email) throws Exception{
        String response = this.appUserService.generatePasswordResetToken(email);
        return ResponseEntity.ok(response);
    }
    @PostMapping("reset_password")
    public void resetPassword(@RequestParam(name = "token") String token, @RequestBody String newPassword) throws Exception{
        this.appUserService.resetPassword(newPassword, token);
    }


    /*****************************************************************************************************/
}
