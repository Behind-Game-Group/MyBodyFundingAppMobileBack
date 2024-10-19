package fr.mybodysocial.mybodyfunding.service.interfaces;

import fr.mybodysocial.mybodyfunding.service.exception.SendMailException;

public interface IEmailSender {

    void sendEmail(String to, String subject, String body) throws SendMailException;
}
