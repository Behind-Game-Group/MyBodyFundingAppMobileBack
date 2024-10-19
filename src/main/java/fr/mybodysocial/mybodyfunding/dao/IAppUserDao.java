package fr.mybodysocial.mybodyfunding.dao;

import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.model.Project;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface IAppUserDao extends IAbstractDao<AppUser> {

    Optional<AppUser> findByEmail(String email);

    List<Project> findInvestmentProjectById(Long id) throws Exception;

    Optional<AppUser> findByTelephone(String telephone);

    Optional<AppUser> findById(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<AppUser> findByResetPasswordToken(String token);

    Optional<AppUser> findByDeleteAccountToken(String token);
}
