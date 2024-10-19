package fr.mybodysocial.mybodyfunding.controller;

import fr.mybodysocial.mybodyfunding.model.AppUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

/**
 * la classe mere de toutes les classes
 */
@RestController
public class AbstractController {

    private static final Logger LOG = LogManager.getLogger();


    protected static final String MSG_AUTH_AND_LL = "Vous devez vous authentifier et avoir le rôle d'un ADMIN!";
    protected static final String MSG_AUTH_OR_LL = "Vous devez vous authentifier ou avoir le rôle d'un CREATEUR!";
    protected static final String MSG_AUTH = "Vous devez vous authentifier!";

    /**
     * Gets the connected user id.
     *
     * @return the connected user id or null if none found
     */
    protected Long getConnectedUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        Long resu = null;
        if (auth != null) {
            var obj = auth.getPrincipal();
            if (obj instanceof AppUser) {
                var user = (AppUser) auth.getPrincipal();
                resu = user != null ? user.getId() : null;
            }
        }
        return resu;
    }
}
