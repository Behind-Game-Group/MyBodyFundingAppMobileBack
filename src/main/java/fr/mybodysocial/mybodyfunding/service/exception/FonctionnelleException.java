package fr.mybodysocial.mybodyfunding.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FonctionnelleException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public FonctionnelleException(String message, Throwable cause) {
        super(message, cause);

    }

    public FonctionnelleException(String message) {
        super(message);

    }

    public FonctionnelleException(Throwable cause) {
        super(cause);

    }

}
