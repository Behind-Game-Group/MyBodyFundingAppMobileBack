package fr.mybodysocial.mybodyfunding.controller;

import fr.mybodysocial.mybodyfunding.dto.out.ExceptionDtoOut;
import fr.mybodysocial.mybodyfunding.service.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GestionnaireErreur {
    @ExceptionHandler(CompteInconnuException.class)
    public ResponseEntity<?> exceptionHandler(CompteInconnuException ce) {
        return ResponseEntity.badRequest().body("le compte n'existe pas");
    }

    @ExceptionHandler(MauvaisMotdepasseException.class)
    public ResponseEntity<?> exceptionHandler(MauvaisMotdepasseException ce) {
        return ResponseEntity.badRequest().body("Mot de passe erroné");
    }

    @ExceptionHandler(EntityAlreadySavedException.class)
    public ResponseEntity<?> exceptionHandler(EntityAlreadySavedException ce) {
        ExceptionDtoOut exceptionDtoOut = new ExceptionDtoOut(ce);
        return ResponseEntity.status(412).body(exceptionDtoOut);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> exceptionHandler(IllegalArgumentException ce) {
        ExceptionDtoOut exceptionDtoOut = new ExceptionDtoOut(ce);
        return ResponseEntity.badRequest().body(exceptionDtoOut);

    }

    @ExceptionHandler(InconsistentStatusException.class)
    public ResponseEntity<?> exceptionHandler(InconsistentStatusException ce) {
        ExceptionDtoOut exceptionDtoOut = new ExceptionDtoOut(ce);
        return ResponseEntity.badRequest().body(exceptionDtoOut);

    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> exceptionHandler(NoSuchElementException ce) {

        return ResponseEntity.notFound().build();

    }

    @ExceptionHandler(ParameterException.class)
    public ResponseEntity<?> exceptionHandler(ParameterException ce) {
        ExceptionDtoOut exceptionDtoOut = new ExceptionDtoOut(ce);
        return ResponseEntity.status(400).body(exceptionDtoOut);

    }
}
