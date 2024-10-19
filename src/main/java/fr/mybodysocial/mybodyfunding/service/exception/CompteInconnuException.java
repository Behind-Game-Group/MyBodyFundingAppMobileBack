package fr.mybodysocial.mybodyfunding.service.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor(force = true)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompteInconnuException extends FonctionnelleException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public CompteInconnuException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public CompteInconnuException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public CompteInconnuException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
