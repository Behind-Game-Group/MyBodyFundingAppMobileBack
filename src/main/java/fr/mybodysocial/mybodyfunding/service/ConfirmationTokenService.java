package fr.mybodysocial.mybodyfunding.service;

import fr.mybodysocial.mybodyfunding.dao.IAppUserDao;
import fr.mybodysocial.mybodyfunding.dao.IConfirmationTokenDao;
import fr.mybodysocial.mybodyfunding.model.ConfirmationToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Data
public class ConfirmationTokenService {

    private final IConfirmationTokenDao confirmationTokenDao;
    private final IAppUserDao appUserDao;

    public void saveConfirmationToken(ConfirmationToken token) {
        if (token == null) {
            throw new IllegalArgumentException("token is null");
        }
        confirmationTokenDao.save(token);
    }

    @Transactional
    public String confirmToken(String token) {

        ConfirmationToken confirmation = confirmationTokenDao.findByToken(token).orElseThrow(() -> new IllegalArgumentException("token not found"));

        if (confirmation.getConfirmedAt() != null) {
            throw new IllegalArgumentException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmation.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("token expired");
        }

        confirmation.setConfirmedAt(LocalDateTime.now());
        confirmation.getAppUser().setEnabled(true);

        return "confirmed";
    }
}
