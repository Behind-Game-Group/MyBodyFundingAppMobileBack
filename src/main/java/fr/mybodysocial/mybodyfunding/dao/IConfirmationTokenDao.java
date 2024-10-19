package fr.mybodysocial.mybodyfunding.dao;

import fr.mybodysocial.mybodyfunding.model.ConfirmationToken;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface IConfirmationTokenDao extends IAbstractDao<ConfirmationToken> {

    Optional<ConfirmationToken> findByToken(String token);
}
