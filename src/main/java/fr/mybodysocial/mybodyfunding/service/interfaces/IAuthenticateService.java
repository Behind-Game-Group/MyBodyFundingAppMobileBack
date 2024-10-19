package fr.mybodysocial.mybodyfunding.service.interfaces;

import fr.mybodysocial.mybodyfunding.dto.in.AppUserDtoIn;
import fr.mybodysocial.mybodyfunding.dto.in.ConnectionDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.LoginAppUserDtoOut;
import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.service.exception.*;

public interface IAuthenticateService {

    AppUser register(AppUserDtoIn request) throws Exception;

    LoginAppUserDtoOut signInUser(ConnectionDtoIn in) throws CompteInconnuException, MauvaisMotdepasseException, InconsistentRoleException;

    AppUser signUpUser(AppUser appUser) throws FonctionnelleException, SendMailException;
}
