package fr.mybodysocial.mybodyfunding.service;

import fr.mybodysocial.mybodyfunding.service.exception.SendMailException;
import fr.mybodysocial.mybodyfunding.service.interfaces.IEmailSender;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class EmailService implements IEmailSender {

    private static final Logger LOG = LogManager.getLogger();

    @NonNull
    private final JavaMailSender mailSender;

    /**
     * Option used to disable send mail
     */
    @Value("${configuration.allow.sendmail}")
    private boolean allowSendmail;


    /**
     * sends confirmed mail
     *
     * @param to      destination
     * @param subject subject
     * @param body    le corp
     * @throws SendMailException
     */
    @Async
    @Override
    public void sendEmail(String to, String subject, String body) throws SendMailException {

        EmailService.LOG.debug("sendSimpleMessage - {} {} {}", to, subject, body);

        if (this.allowSendmail) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("mybody.funding@gmail.com");
                message.setTo(to);
                message.setText(body);
                message.setSubject(subject);
                mailSender.send(message);
                EmailService.LOG.debug("sendSimpleMessage- Message sent to {}", to);
            } catch (Exception e) {
                throw new SendMailException("Erreurs lors de l'envoi de l'email", e);

            }
        } else {
            EmailService.LOG.fatal("sendSimpleMessage -Ok BUT send email is desactivated");
        }

    }


    /**
     * Activates send mail.
     */
    public void activateSendMail() {
        this.allowSendmail = true;
    }

    /**
     * Deactivate send mail.
     */
    public void deactivateSendMail() {
        this.allowSendmail = false;
    }

}
