package fr.mybodysocial.mybodyfunding.service;

import fr.mybodysocial.mybodyfunding.dao.IAppUserDao;
import fr.mybodysocial.mybodyfunding.dto.handler.UserDtoHandler;
import fr.mybodysocial.mybodyfunding.dto.in.AppUserDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.ConnectionDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.LoginAppUserDtoOut;
import fr.mybodysocial.mybodyfunding.enums.SituationFamilliale;
import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.model.ConfirmationToken;
import fr.mybodysocial.mybodyfunding.service.exception.*;
import fr.mybodysocial.mybodyfunding.service.interfaces.IAuthenticateService;
import fr.mybodysocial.mybodyfunding.service.interfaces.IEmailSender;
import fr.mybodysocial.mybodyfunding.util.Constante;
import fr.mybodysocial.mybodyfunding.util.EmailBuilder;
import fr.mybodysocial.mybodyfunding.validator.EmailValidator;
import fr.mybodysocial.mybodyfunding.validator.NotAdminValidator;
import fr.mybodysocial.mybodyfunding.validator.PasswordValidator;
import fr.mybodysocial.mybodyfunding.validator.TelephoneValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthentificationService implements IAuthenticateService {
    private static final Logger LOG = LogManager.getLogger();
    public final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    public final static String USER_NOT_FOUND_MSG2 = "user with id %s not found";

    private final IAppUserDao appUserDao;
    private EmailValidator emailValidator;
    private TelephoneValidator telephoneValidator;
    private PasswordValidator passwordValidator;

    private NotAdminValidator notAdminValidator;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    private final IEmailSender emailSender;

    private final JWTService jwt;
    @Override
    public AppUser register(AppUserDtoIn request) throws Exception {
        this.emailValidator = new EmailValidator();
        this.telephoneValidator = new TelephoneValidator();
        this.passwordValidator = new PasswordValidator();
        this.notAdminValidator = new NotAdminValidator();

        boolean isEmailValid = emailValidator.test(request.getEmail());
        boolean passwordValidator = this.passwordValidator.test(request.getPassword());
        boolean isTelephoneValid = telephoneValidator.test(request.getTelephone());
        boolean isNotAdmin = this.notAdminValidator.test(request.getRole());
        boolean isTelephoneProcheValid = false;

        if(!passwordValidator){
            throw new ParameterException("Password dont match the pattern");
        }
        if (request.getTelephone_proche() != null) {
            isTelephoneProcheValid = telephoneValidator.test(request.getTelephone_proche());
        }
        if (!isEmailValid) {
            throw new IllegalArgumentException("email not valid");
        }
        if (!isTelephoneValid) {
            throw new ParameterException("le numero de telephone is not valid");
        }
        if (request.getNom().trim().isEmpty() || request.getNom() == null || request.getPrenom().trim().isEmpty() || request.getPrenom() == null || request.getUsername().trim().isEmpty() || request.getTelephone().trim().isEmpty() || request.getTelephone() == null || request.getNat().trim().isEmpty() || request.getNat() == null || request.getDate_naissance() == null || request.getDate_naissance().trim().isEmpty() || request.getSituationFamilliale() == null || request.getGenre() == null || request.getPassword().trim().isEmpty() || request.getPassword() == null || request.getRole() == null) {
            throw new ParameterException("un ou plusieurs parametres manquants....!");
        }
        if (request.getSituationFamilliale().equals(SituationFamilliale.marié) && (request.getTelephone_proche().trim().isEmpty() || request.getTelephone_proche() == null)) {
            throw new ParameterException("vous devez completer le numero d'un proche");
        }
        if (request.getSituationFamilliale().equals(SituationFamilliale.marié) && !isTelephoneProcheValid) {
            throw new IllegalArgumentException("telephone proche not valid");
        }
        if(!isNotAdmin){
            throw new IllegalArgumentException("Cannot be ADMIN");
        }
        AppUser newUser = UserDtoHandler.toEntity(request);
        newUser.setDate_creation(new Date());
        AppUser savedUser = signUpUser(newUser);
        return savedUser;
    }
    @Override
    public AppUser signUpUser(AppUser appUser) throws FonctionnelleException, SendMailException {
        boolean isExiste = appUserDao.findByEmail(appUser.getEmail()).isPresent();
        if (isExiste) {
            throw new EntityAlreadySavedException("Utilisateur avec email=[" + appUser.getEmail() + "] est deja dans la base de donnees.");
        }
        String encodePassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodePassword);
        AppUser savedUser = appUserDao.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        /* On envoie le mail de confirmation */
        String link = "http://localhost:8080/user/confirm?token=" + token;
        emailSender.sendEmail(appUser.getEmail(), "confirmed mail my body funding", EmailBuilder.buildTokenConfirmationEmail(appUser.getNom(), link));
        return savedUser;
    }
    /* Login Method */
    @Override
    public LoginAppUserDtoOut signInUser(ConnectionDtoIn in) throws CompteInconnuException, MauvaisMotdepasseException, InconsistentRoleException {
        AppUser appUser = appUserDao.findByEmail(in.getEmail()).orElseThrow(CompteInconnuException::new);
        if (this.bCryptPasswordEncoder.matches(in.getPassword(), appUser.getPassword())) {
            if (appUser.getLocked() || !appUser.getEnabled()) {
                throw new InconsistentRoleException();
            }
            return UserDtoHandler.entityToLoginApp(appUser, jwt.generateToken(appUser));
        } else {
            throw new MauvaisMotdepasseException();
        }
    }
    public HttpHeaders putTokenOnHeaders(AppUser user) {
        var jwtToken = jwt.generateToken(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(Constante.HEADERS_KEY, Constante.HEADERS_PREFIX + jwtToken);
        return responseHeaders;
    }
}
