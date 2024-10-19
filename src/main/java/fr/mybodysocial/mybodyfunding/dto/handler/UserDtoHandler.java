package fr.mybodysocial.mybodyfunding.dto.handler;

import fr.mybodysocial.mybodyfunding.dto.in.AppUserDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.AppUserDtoOut;
import fr.mybodysocial.mybodyfunding.dto.out.LoginAppUserDtoOut;
import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.util.Dates;

import java.util.ArrayList;
import java.util.List;

public interface UserDtoHandler {

    /**
     * transformer un Userdto a un UserEntité
     *
     * @param pDto DTO In
     * @return entity
     */
    static AppUser toEntity(AppUserDtoIn pDto) throws Exception {
        var result = new AppUser();
        result.setNom(pDto.getNom().toUpperCase());
        result.setPrenom(pDto.getPrenom().trim().substring(0, 1).toUpperCase()
                .concat(pDto.getPrenom().trim().substring(1).toLowerCase()));
        result.setUsername(pDto.getUsername());
        result.setPassword(pDto.getPassword());
        result.setEmail(pDto.getEmail());
        result.setTelephone(pDto.getTelephone());
        result.setAvatar(pDto.getAvatar());
        result.setNationality(pDto.getNat());
        result.setDate_naissance(Dates.stringToDate(pDto.getDate_naissance()));
        result.setSituation_familliale(pDto.getSituationFamilliale());
        result.setTelephoneProche(pDto.getTelephone_proche());
        result.setGenre(pDto.getGenre());
        result.setAppUserRole(pDto.getRole());
        return result;

    }

    /**
     * transform entity to dtoout
     *
     * @param pEntity entité
     * @return dto Out
     */
    static AppUserDtoOut dtoOutFromEntity(AppUser pEntity) {
        var result = new AppUserDtoOut();
        result.setId(pEntity.getId());
        result.setNom(pEntity.getNom());
        result.setPrenom(pEntity.getPrenom());
        result.setUsername(pEntity.getUsername());
        result.setEmail(pEntity.getEmail());
        result.setTelephone(pEntity.getTelephone());
        result.setAvatar(pEntity.getAvatar());
        result.setNat(pEntity.getNationality());
        result.setDate_naissance(pEntity.getDate_naissance());
        result.setDate_creation(pEntity.getDate_creation());
        result.setSituationFamilliale(pEntity.getSituation_familliale());
        result.setTelephone_proche(pEntity.getTelephoneProche());
        result.setGenre(pEntity.getGenre());
        result.setRole(pEntity.getAppUserRole());
        result.setLocked(pEntity.getLocked());
        result.setEnabled(pEntity.getEnabled());
        return result;
    }

    /**
     * transforme some entities to dto out
     *
     * @param appUsers liste of entities
     * @return liste of dto Out
     */
    static List<AppUserDtoOut> dtosOutfromEntities(List<AppUser> appUsers) {
        List<AppUserDtoOut> appUserDtoOuts = new ArrayList<>();

        if (appUsers != null && !appUsers.isEmpty()) {
            appUsers.forEach(elm -> appUserDtoOuts.add(UserDtoHandler.dtoOutFromEntity(elm)));
        }
        return appUserDtoOuts;
    }

    static LoginAppUserDtoOut entityToLoginApp(AppUser user, String jwtToken){
        var result = new LoginAppUserDtoOut();
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setUsername(user.getUsername());
        result.setJwtToken(jwtToken);
        return result;
    }
}
