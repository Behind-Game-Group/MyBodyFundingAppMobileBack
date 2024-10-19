package fr.mybodysocial.mybodyfunding.service;

import fr.mybodysocial.mybodyfunding.dao.IAppUserDao;
import fr.mybodysocial.mybodyfunding.dao.ICommentProjectDao;
import fr.mybodysocial.mybodyfunding.dto.handler.CommentProjectDtoHandler;
import fr.mybodysocial.mybodyfunding.dto.handler.InvestmentDtoHandler;
import fr.mybodysocial.mybodyfunding.dto.handler.ProjectDtoHandler;
import fr.mybodysocial.mybodyfunding.dto.handler.UserDtoHandler;
import fr.mybodysocial.mybodyfunding.dto.in.AppUserDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.ForgetPasswordDto;
import fr.mybodysocial.mybodyfunding.dto.out.AppUserDtoOut;
import fr.mybodysocial.mybodyfunding.dto.out.CommentProjectDtoOut;
import fr.mybodysocial.mybodyfunding.dto.out.InvestmentDtoOut;
import fr.mybodysocial.mybodyfunding.dto.out.ProjectDtoOut;
import fr.mybodysocial.mybodyfunding.enums.SituationFamilliale;
import fr.mybodysocial.mybodyfunding.enums.UserStatus;
import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.model.CommentProject;
import fr.mybodysocial.mybodyfunding.model.Investment;
import fr.mybodysocial.mybodyfunding.service.exception.EntityAlreadySavedException;
import fr.mybodysocial.mybodyfunding.service.exception.EntityNotFoundException;
import fr.mybodysocial.mybodyfunding.service.exception.InconsistentStatusException;
import fr.mybodysocial.mybodyfunding.service.exception.ParameterException;
import fr.mybodysocial.mybodyfunding.service.interfaces.IEmailSender;
import fr.mybodysocial.mybodyfunding.util.Dates;
import fr.mybodysocial.mybodyfunding.util.EmailBuilder;
import fr.mybodysocial.mybodyfunding.validator.EmailValidator;
import fr.mybodysocial.mybodyfunding.validator.NotAdminValidator;
import fr.mybodysocial.mybodyfunding.validator.PasswordValidator;
import fr.mybodysocial.mybodyfunding.validator.TelephoneValidator;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private static final Logger LOG = LogManager.getLogger();

    public final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    public final static String USER_NOT_FOUND_MSG2 = "user with id %s not found";

    private final IAppUserDao appUserDao;
    private final ICommentProjectDao iCommentDao;

    private EmailValidator emailValidator;
    private TelephoneValidator telephoneValidator;
    private PasswordValidator passwordValidator;
    private NotAdminValidator notAdminValidator;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    private final IEmailSender emailSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        AppUserService.LOG.info("UserDetails - {} ", appUserDao.findByEmail(email).get());
        return appUserDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }


    @Transactional(rollbackFor = Exception.class)
    public AppUser update(Long userId, AppUserDtoIn newUser) throws Exception {

        AppUserService.LOG.info("update - {} with {}", userId, newUser);
        AppUser userBD = appUserDao.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MSG2, userId));

        if (newUser == null) {
            AppUserService.LOG.error("update - null?");
            throw new ParameterException("l'utilisateur est null !", "newUser");
        }
        //verifier format d'email
        if (newUser.getEmail() != null) {
            boolean isEmailValid = emailValidator.test(newUser.getEmail());
            if (!isEmailValid) {
                throw new ParameterException("verifier le format d'email");
            }
        }

        if (newUser.getTelephone() != null) {
            boolean isTelephoneValid = telephoneValidator.test(newUser.getTelephone());
            if (!isTelephoneValid) {
                throw new ParameterException("mettez un numero de telephone correct...!");
            }
        }
        if (newUser.getEmail() != null && !newUser.getEmail().equals((userBD.getEmail()))) {
            AppUserService.LOG.debug("update -User email has changed");
            // verifier si le nouveau mail saisie n'existe pas dans la base de donnees
            if (this.exist(newUser.getEmail())) {
                AppUserService.LOG.error("update - user already in data base {}", newUser.getEmail());
                throw new EntityAlreadySavedException("user avec email =[ " + newUser.getEmail() + " ] existe deja dans la base de donnees");
            }
            userBD.setEmail(newUser.getEmail());

        }
        if (newUser.getTelephone() != null && !newUser.getTelephone().equals((userBD.getTelephone()))) {
            AppUserService.LOG.debug("update -User telephone has changed");
            // verifier si le nouveau numero de telephone saisie n'existe pas dans la base de donnees
            if (this.existTelephone((newUser.getTelephone()))) {
                AppUserService.LOG.error("update - le numero de telephone  already exist in data base {}", newUser.getTelephone());
                throw new EntityAlreadySavedException("user avec le numero de telephone  =[ " + newUser.getTelephone() + " ] existe deja dans la base de donnees");
            }
            userBD.setTelephone(newUser.getTelephone());
        }
        if (newUser.getPassword() != null) {
            newUser.setPassword(this.bCryptPasswordEncoder.encode(newUser.getPassword()));
            if (!newUser.getPassword().equals(userBD.getPassword())) {
                if(!passwordValidator.test(newUser.getPassword())){
                    throw new ParameterException("Password dont match the pattern");
                }
                AppUserService.LOG.debug("update -User mdp has changed");
                userBD.setPassword(newUser.getPassword());
            }
        }
        if (newUser.getNom() != null) {
            if (!newUser.getNom().equals(userBD.getNom())) {
                AppUserService.LOG.debug("update - user name has changed");
                userBD.setNom(newUser.getNom().toUpperCase());
            }
        }
        if (newUser.getPrenom() != null) {
            if (!newUser.getPrenom().equals(userBD.getPrenom())) {
                AppUserService.LOG.debug("update - user prenom has changed");
                userBD.setPrenom(newUser.getPrenom());
            }
        }
        if (newUser.getUsername() != null) {
            if (!newUser.getUsername().equals(userBD.getUsername())) {
                AppUserService.LOG.debug("update - username has changed");
                userBD.setUsername(newUser.getUsername());
            }
        }
        if (newUser.getAvatar() != null) {
            if (!newUser.getAvatar().equals(userBD.getAvatar())) {
                AppUserService.LOG.debug("update - avatar has changed");
                userBD.setAvatar(newUser.getAvatar());
            }
        }
        if (newUser.getNat() != null) {
            if (!newUser.getNat().equals(userBD.getNationality())) {
                AppUserService.LOG.debug("update - nationnalité has changed");
                userBD.setNationality(newUser.getNat());
            }
        }
        if (newUser.getDate_naissance() != null) {
            if (!newUser.getDate_naissance().equals(userBD.getDate_naissance())) {
                AppUserService.LOG.debug("update - date de naissance has changed");
                userBD.setDate_naissance(Dates.stringToDate(newUser.getDate_naissance()));
            }
        }
        if (newUser.getGenre() != null) {
            if (!newUser.getGenre().equals(userBD.getGenre())) {
                AppUserService.LOG.debug("update - genre has changed");
                userBD.setGenre(newUser.getGenre());
            }
        }
        if (newUser.getSituationFamilliale() != null) {

            if (newUser.getSituationFamilliale().equals(SituationFamilliale.marié) && newUser.getTelephone_proche() == null) {
                throw new ParameterException("la case telephone proche ne doit pas etre vide");
            }
            if (!newUser.getSituationFamilliale().equals(userBD.getSituation_familliale())) {
                AppUserService.LOG.debug("update - situation familliale has changed");
                userBD.setSituation_familliale(newUser.getSituationFamilliale());
            }

        }
        if (newUser.getTelephone_proche() != null) {
            boolean isTelephoneProcheValid = telephoneValidator.test(newUser.getTelephone_proche());
            if (!isTelephoneProcheValid) {
                throw new ParameterException("verifier le format du numero de telephne proche");
            }
            if (!newUser.getTelephone_proche().equals(userBD.getTelephoneProche())) {
                AppUserService.LOG.debug("update - le numero de telephone d'un proche has changed");
                userBD.setTelephoneProche((newUser.getTelephone_proche()));
            }
        }

        if(!this.notAdminValidator.test(newUser.getRole())){
            throw new IllegalArgumentException("Cannot choice admin !");
        }

        var result = appUserDao.save(userBD);
        AppUserService.LOG.info("update -new User {}", result);
        AppUserService.LOG.info("update -Ok");
        return result;
    }

    /**
     * indicates if user with given email is already existe in db
     *
     * @param email
     * @return true if email exist in db ,false if not
     * @throws ParameterException if parameter is invalid
     */
    @Transactional(readOnly = true)
    public boolean exist(String email) {
        if (email == null) {
            AppUserService.LOG.error("find -null?");
            throw new ParameterException("Email est null !", "email");
        }
        var foundUser = this.appUserDao.findByEmail(email);
        return foundUser.isPresent();
    }

    @Transactional(readOnly = true)
    public boolean existTelephone(String telephone) {
        if (telephone == null) {
            AppUserService.LOG.error("find -null?");
            throw new ParameterException("telephone est null !", "telephone");
        }
        var foundUser = this.appUserDao.findByTelephone(telephone);
        return foundUser.isPresent();
    }


    /**
     * enable the user. <br>
     *
     * @param pUserId a user id
     * @return the user enabled
     * @throws EntityNotFoundException if entity not found
     * @throws ParameterException      if parameter is invalid
     */
    @Transactional(rollbackFor = Exception.class)
    public AppUser enable(Long pUserId) throws EntityNotFoundException, ParameterException, InconsistentStatusException {
        AppUserService.LOG.debug("enable - {}", pUserId);
        return this.updateStatus(pUserId, UserStatus.ENABLED);
    }

    /**
     * Disables the user. <br>
     * <p>
     * A disabled user cannot login not change any thing.
     *
     * @param pUserId a user id
     * @return the user disabled
     * @throws EntityNotFoundException if entity not found
     * @throws ParameterException      if parameter is invalid
     */
    @Transactional(rollbackFor = Exception.class)
    public AppUser disable(Long pUserId) throws EntityNotFoundException, ParameterException, InconsistentStatusException {
        AppUserService.LOG.debug("disable - {}", pUserId);
        return this.updateStatus(pUserId, UserStatus.DISABLED);
    }

    private AppUser updateStatus(Long pUserId, UserStatus userStatus) throws EntityNotFoundException, InconsistentStatusException {
        AppUserService.LOG.debug("updateStatus - {} for new state {}", pUserId, userStatus);
        if (pUserId == null) {
            AppUserService.LOG.error("updateStatus - id is null");
            throw new ParameterException("Utilisateur n'a pas d'id !", "pUserId");
        }
        if (userStatus == null) {
            AppUserService.LOG.error("updateStatus - newState is null");
            throw new ParameterException("Etat est null", "pNewUserStatus");
        }
        AppUser userBD = appUserDao.findById(pUserId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MSG2, pUserId));
        if (userBD.getEnabled() == userStatus.isValue()) {
            throw new InconsistentStatusException(
                    "Utilisateur ayant l'id " + pUserId + " est déjà dans l'état demandé (" + userStatus + ")");
        }
        userBD.setEnabled(userStatus.isValue());
        var resultUpdate = appUserDao.save(userBD);
        AppUserService.LOG.info("updateStatus - OK");
        return resultUpdate;
                
    }


    /**
     * find all users
     *
     * @return
     */
    public List<AppUser> findAllUsers() {
        var result = appUserDao.findAll();
        if (result.isEmpty()) {
            throw new NoSuchElementException("la liste est vide");
        }
        return result;
    }

    /*** RESET PASSWORD SYSTEM ***********************************************************************************/

    public String generatePasswordResetToken(ForgetPasswordDto email) throws Exception{
        AppUser user = this.appUserDao.findByEmail(email.getEmail()).orElseThrow(EntityNotFoundException::new);
        String token = UUID.randomUUID().toString();

        user.setResetPasswordToken(token);
        user.setEnabled(false);
        String link="http://localhost:8080/user/resetPassword?token="+token;
        emailSender.sendEmail(user.getEmail(), "Reset password mail my body funding", EmailBuilder.buildTokenResetEmail(user.getNom(), link));
        this.appUserDao.save(user);
        return link;
    }

    public AppUserDtoOut getUserByResetPasswordToken(String token) throws Exception{
        AppUser user = this.appUserDao.findByResetPasswordToken(token).orElseThrow(EntityNotFoundException::new);
        return UserDtoHandler.dtoOutFromEntity(user);
    }

    public void resetPassword(String newPassword, String token) throws Exception{
        if(this.passwordValidator.test(newPassword)) {
            AppUser user = this.appUserDao.findByResetPasswordToken(token).orElseThrow(EntityNotFoundException::new);
            String encodePassword = bCryptPasswordEncoder.encode(newPassword);
            user.setPassword(encodePassword);
            user.setResetPasswordToken(null);
            user.setEnabled(true);
            this.appUserDao.save(user);
        }else{
            throw new ParameterException();
        }
    }

    /***************************************************************************************************************/


    /*** DELETE ACCOUNT SYSTEM ***********************************************************************************/

    public void generateDeleteAccountToken(Long userId) throws Exception{
        AppUser user = this.appUserDao.findById(userId).orElseThrow(EntityNotFoundException::new);
        String token = UUID.randomUUID().toString();
        user.setDeleteAccountToken(token);
        String link="http://localhost:8080/user/deleteAccount?token="+token;
        emailSender.sendEmail(user.getEmail(), "Delete account confirmation my body funding", EmailBuilder.buildTokenResetEmail(user.getNom(), link));
        this.appUserDao.save(user);
    }


    public void deleteUserWithDeleteTokenAccount(String token) throws Exception{
        AppUser user = this.appUserDao.findByDeleteAccountToken(token).orElseThrow(EntityNotFoundException::new);
        delete(user.getId());
    }

    /**
     * Deletes the user. <br>
     *
     * @param pUserId a user id
     * @return the user deleted
     * @throws EntityNotFoundException     if entity not found
     * @throws InconsistentStatusException if entity state is not valid
     * @throws ParameterException          if parameter is invalid
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long pUserId) throws EntityNotFoundException {
        AppUserService.LOG.debug("delete - {}", pUserId);
        if (pUserId == null) {
            throw new ParameterException("id user is null ...!");
        }
        AppUser userBD = appUserDao.findById(pUserId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MSG2, pUserId));
        appUserDao.deleteById(pUserId);
    }

    /***************************************************************************************************************/

    public List<CommentProjectDtoOut> getUserAllComments(Long id) throws Exception {
        AppUser appUser = appUserDao.findById(id).orElseThrow(EntityNotFoundException::new);
        List<CommentProject> comments = this.iCommentDao.getCommentProjectByAuthor(appUser);
        return CommentProjectDtoHandler.entitestoDto(comments);
    }

    public List<InvestmentDtoOut> getUserAllInvestments(Long id) throws Exception {
        AppUser appUser = appUserDao.findById(id).orElseThrow(EntityNotFoundException::new);
        List<Investment> out = appUser.getInvestmentProject();
        return InvestmentDtoHandler.entitestoDto(out);
    }

    public List<ProjectDtoOut> getAllProjectLiked(Long userId) throws Exception {
        AppUser appUser = appUserDao.findById(userId).orElseThrow(() -> new Exception("Utilisateur non trouvé"));
        return ProjectDtoHandler.entitestoDto(appUser.getProjectsLiked());
    }
}
